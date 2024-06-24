package com.reader.bacalagi.data_area.utils

import com.reader.bacalagi.data_area.BuildConfig

object DataProvider {
    var databaseName: String = BuildConfig.DATABASE_NAME
    var baseUrl: String = BuildConfig.BASE_URL
}