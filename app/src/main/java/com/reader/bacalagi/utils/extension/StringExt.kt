package com.reader.bacalagi.utils.extension

fun String.toCapitalCase(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

fun String.parsePhoneNumber(): String {
    val countryCode = "+62"
    val phoneNumberDigits = this.trimStart('6', '2')
    return countryCode + phoneNumberDigits
}