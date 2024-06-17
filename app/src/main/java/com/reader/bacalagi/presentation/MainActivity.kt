package com.reader.bacalagi.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.reader.bacalagi.R
import com.reader.bacalagi.data.utils.helper.network_state.InternetConnectionCallback
import com.reader.bacalagi.data.utils.helper.network_state.InternetConnectionObserver
import com.reader.bacalagi.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Locale

class MainActivity : AppCompatActivity(), InternetConnectionCallback {

    private val viewModel: MainActivityViewModel by viewModel()

    private var _binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        viewModel.getThemeSettings().observe(this) { isDarkMode ->
            Timber.tag("MainActivity").v("isDarkMode: $isDarkMode")

            AppCompatDelegate.setDefaultNightMode(isDarkMode?.let {
                if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            } ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        }

        viewModel.getLocaleSettings().observe(this) { locale ->
            Timber.tag("MainActivity").v("locale: $locale")

            AppCompatDelegate.setApplicationLocales(
                if (locale.isNullOrEmpty()) {
                    LocaleListCompat.forLanguageTags(Locale.US.language)
                } else {
                    LocaleListCompat.forLanguageTags(
                        locale
                    )
                }
            )
        }

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