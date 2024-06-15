package com.reader.bacalagi.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.reader.bacalagi.R
import com.reader.bacalagi.data.utils.helper.network_state.InternetConnectionCallback
import com.reader.bacalagi.data.utils.helper.network_state.InternetConnectionObserver
import com.reader.bacalagi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), InternetConnectionCallback {

    private var _binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        InternetConnectionObserver
            .instance(this)
            .setCallback(this)
            .register()


    }

    override fun onDestroy() {
        _binding = null
        InternetConnectionObserver.unRegister()
        super.onDestroy()
    }

    override fun onConnected() {
        runOnUiThread {
            val handler = Handler(Looper.getMainLooper())

            _binding?.tvConnectionStatus?.text = getString(R.string.notif_connection_connected)
            _binding?.tvConnectionStatus?.setBackgroundColor(getColor(R.color.primary_40))

            handler.postDelayed({
                _binding?.tvConnectionStatus?.visibility = View.GONE
            }, 2000)
        }
    }

    override fun onDisconnected() {
        runOnUiThread {
            _binding?.tvConnectionStatus?.text = getString(R.string.notif_connection_disconnected)
            _binding?.tvConnectionStatus?.setBackgroundColor(getColor(R.color.error_50))
            _binding?.tvConnectionStatus?.visibility = View.VISIBLE
        }
    }
}