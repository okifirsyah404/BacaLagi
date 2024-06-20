package com.reader.bacalagi.data.utils

import com.reader.bacalagi.data.BuildConfig

object DataProvider {
    var databaseName: String = BuildConfig.DATABASE_NAME


    var baseUrl: String = BuildConfig.BASE_URL
//    var baseUrl: String = "https://emerging-moccasin-ghastly.ngrok-free.app/"

    var areaBaseUrl: String = BuildConfig.AREA_BASE_URL
}