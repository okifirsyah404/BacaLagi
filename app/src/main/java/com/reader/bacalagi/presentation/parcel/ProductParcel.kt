package com.reader.bacalagi.presentation.parcel

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class ProductParcel(
    val title: String,
    val author: String,
    val ISBN: String,
    val publisher: String,
    val publishYear: String,
    val language: String,
    val imageUri: Uri,
    val buyPrice: String,
    val description: String,
    val predictionResult: String
) : Parcelable