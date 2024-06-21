package com.reader.bacalagi.data.utils

import com.reader.bacalagi.data.BuildConfig

object DataProvider {
    var databaseName: String = BuildConfig.DATABASE_NAME


    var baseUrl: String = "https://api.readerlab.my.id/"
//    var baseUrl: String = BuildConfig.BASE_URL

    var areaBaseUrl: String = BuildConfig.AREA_BASE_URL
}