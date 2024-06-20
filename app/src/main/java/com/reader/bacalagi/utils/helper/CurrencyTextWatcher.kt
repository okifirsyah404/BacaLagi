package com.reader.bacalagi.utils.helper

import android.text.Editable
import android.text.TextWatcher

class CurrencyTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        TODO("Not yet implemented")
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotEmpty()) {
                it.insert(0, "Rp ")
            }
        }
    }
}