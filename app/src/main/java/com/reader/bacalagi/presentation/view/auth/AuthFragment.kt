package com.reader.bacalagi.presentation.view.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.dto.AuthDto
import com.reader.bacalagi.databinding.FragmentAuthBinding
import com.reader.bacalagi.domain.utils.extension.observeSingleEventResult
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import com.reader.bacalagi.utils.helper.MutableReference
import com.reader.bacalagi.utils.provider.FirebaseProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient

    private val loadingDialogReference = MutableReference<AlertDialog?>(null)

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    val credential =
                        Identity.getSignInClient(requireActivity())
                            .getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credential.googleIdToken
                    if (googleIdToken != null) {
                        firebaseAuthWithGoogle(googleIdToken)
                    } else {
                        Log.e("MainActivity", "No Google ID token!")
                    }
                } catch (e: ApiException) {
                    Log.w("MainActivity", "Google sign in failed", e)
                    Toast.makeText(
                        requireActivity(),
                        "Google sign in failed: ${e.statusCode}",
                        Toast.LENGTH_SHORT
                    ).show()
                    handleSignInError(e.statusCode)
                }
            }
        }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(requireActivity())
    }

    override fun initActions() {
        binding.btnGoogleSignIn.setOnClickListener {
            signIn()
        }
    }

    override fun initObservers() {
        viewModel.authResult.observeSingleEventResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }
            onError = { errorMessage ->
                showLoading(false)
                showError(true, errorMessage)
            }

            onSuccess = {
                showLoading(false)
                showError(false, "")
                onResult(it)
            }

        }
    }

    private fun onResult(data: AuthDto) {

        Timber.tag("AuthFragment").d("Data: $data")
        Timber.tag("AuthFragment").d("Data: ${data.isRegistered}")
        Timber.tag("AuthFragment").d("Data: ${data.accessToken}")

        if (data.isRegistered) {
            findNavController().navigate(R.id.action_authFragment_to_dashboardFragment)
        } else {
            findNavController().navigate(R.id.action_authFragment_to_registerFragment)
        }

        return
    }

    override fun showLoading(isLoading: Boolean) {
        showLoadingDialog(
            loading = isLoading,
            dialogReference = loadingDialogReference
        )
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            showSingleActionDialog(
                title = "Error",
                message = message
            )
        }
    }

    private fun signIn() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(FirebaseProvider.webClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                signInLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.e("MainActivity", "Google sign in failed", e)
                Toast.makeText(
                    requireActivity(),
                    "Google sign in failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    Toast.makeText(
                        requireActivity(),
                        "Authentication successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(requireActivity(), "Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        auth.currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val _idToken = task.result?.token

                    Timber.tag("GoogleSignInFragment").d("ID Token: $_idToken")
                    viewModel.auth(_idToken ?: "")


                    Timber.tag("GoogleSignInFragment").d("ID Token: $_idToken")
                } else {
                    Log.e("GoogleSignInFragment", "Error getting ID token.")
                }
            }
    }

    private fun handleSignInError(statusCode: Int) {
        when (statusCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> {
                Toast.makeText(requireActivity(), "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            }

            GoogleSignInStatusCodes.SIGN_IN_FAILED -> {
                Toast.makeText(requireActivity(), "Sign-in failed", Toast.LENGTH_SHORT).show()
            }

            GoogleSignInStatusCodes.NETWORK_ERROR -> {
                Toast.makeText(requireActivity(), "Network error", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(requireActivity(), "Sign-in error: $statusCode", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}