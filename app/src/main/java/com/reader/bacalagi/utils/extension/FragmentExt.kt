package com.reader.bacalagi.utils.extension

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reader.bacalagi.R
import com.reader.bacalagi.data.utils.helper.network_state.InternetConnectionObserver
import com.reader.bacalagi.utils.ObserveConnectionConfig
import com.reader.bacalagi.utils.helper.MutableReference

fun Fragment.showLoadingDialog(
    title: String = "Loading",
    message: String = "Please wait...",
    loading: Boolean,
    dialogReference: MutableReference<AlertDialog?>
) {
    if (loading) {
        if (dialogReference.value == null) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_progress, null)
            dialogView.findViewById<TextView>(R.id.progress_message).text = message

            val builder = MaterialAlertDialogBuilder(requireActivity()).apply {
                setTitle(title)
                setView(dialogView)
                setCancelable(false)
            }
            dialogReference.value = builder.create()
            dialogReference.value?.show()
        }
    } else {
        dialogReference.value?.dismiss()
        dialogReference.value = null
    }
}

fun Fragment.showSingleActionDialog(
    title: String,
    message: String,
    onTapLabel: String = "Ok",
    onTap: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(requireActivity()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(onTapLabel) { p0, _ ->
            onTap?.invoke() ?: p0.dismiss()
        }
        setOnDismissListener {
            it.dismiss()
        }
    }.show()
}

fun Fragment.showDecisionDialog(
    title: String,
    message: String,
    onYesLabel: String = "Yes",
    onNoLabel: String = "No",
    onYes: (() -> Unit)? = null,
    onNo: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(requireActivity()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(onYesLabel) { p0, _ ->
            onYes?.invoke() ?: p0.dismiss()
        }
        setNegativeButton(onNoLabel) { p0, _ ->
            onNo?.invoke() ?: p0.dismiss()
        }
        setOnDismissListener {
            it.dismiss()
        }
    }.show()
}

fun Fragment.observeInternetConnection(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    config: ObserveConnectionConfig.() -> Unit
) {
    val internetConnectionObserver = InternetConnectionObserver.instance(context).networkStatus
    internetConnectionObserver.observe(lifecycleOwner) { isConnected ->
        if (!isConnected) {
            ObserveConnectionConfig().apply(config).onDisconnected?.invoke()
        } else {
            ObserveConnectionConfig().apply(config).onConnected?.invoke()
        }
    }
}