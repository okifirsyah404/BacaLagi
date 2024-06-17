import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kspKotlinAndroid)

    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("B:\\Bangkit\\Capstone\\keystore\\debug.jks")
            storePassword = "reader"
            keyAlias = "debug"
            keyPassword = "reader"
        }
    }
    namespace = "com.reader.bacalagi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reader.bacalagi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            "String",
            "WEB_CLIENT_ID",
            "\"${properties.getProperty("WEB_CLIENT_ID")}\""
        )
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Submodules
    implementation(project(":domain"))
    implementation(project(":data"))

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.auth)
    implementation(libs.firebase.analytics)

    //  Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //  Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //  Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Coil
    implementation(libs.coil)

    //  Timber
    implementation(libs.timber)

    // Preferences DataStore
    implementation(libs.androidx.datastore.preferences)

    // Image View
    implementation(libs.circleimageview)
    implementation(libs.roundedimageview)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)

    //UCrop
    implementation(libs.ucrop)
}