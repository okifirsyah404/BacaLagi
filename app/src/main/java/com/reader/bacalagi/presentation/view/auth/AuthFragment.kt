package com.reader.bacalagi.presentation.view.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private val RC_SIGN_IN = 9001

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
//        TODO("Not yet implemented")
        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(requireActivity())
    }

    override fun initActions() {
        binding.btnGoogleSignIn.setOnClickListener {
//            findNavController().navigate(R.id.action_authFragment_to_dashboardFragment)
            signIn()
        }
    }

    private fun signIn() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
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
                    Log.d("MainActivity", "signInWithCredential:success")
                    Log.d("GoogleSignInFragment", "signInWithCredential:success")
                    Log.d("GoogleSignInFragment", "Token: $idToken")
                    Log.d("GoogleSignInFragment", "User: ${user?.uid}")
                    Log.d("GoogleSignInFragment", "User: ${user?.displayName}")
                    Log.d("GoogleSignInFragment", "User: ${user?.email}")
                    Log.d("GoogleSignInFragment", "User: ${user?.photoUrl}")
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