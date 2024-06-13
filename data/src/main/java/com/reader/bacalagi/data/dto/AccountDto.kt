package com.reader.bacalagi.data.dto

import com.reader.bacalagi.data.network.response.AccountResponse

data class AccountDto(
    val id: String,
    val email: String,
    val googleID: String
) {
    companion object {
        fun fromResponse(response: AccountResponse?) = response?.let {
            AccountDto(
                id = response.id,
                email = response.email,
                googleID = response.googleID
            )
        }
    }
}
