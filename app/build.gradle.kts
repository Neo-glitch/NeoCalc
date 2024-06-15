import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspAndroid)
    alias(libs.plugins.hilt)
//    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

val keyStorePropertiesFile = rootProject.file("keystore.properties")
val keyStoreProperties = Properties()
keyStoreProperties.load(FileInputStream(keyStorePropertiesFile))

android {
    namespace = "com.neocalc.neocalc"
    compileSdk = 34

    signingConfigs {
        create("release"){
            keyAlias = keyStoreProperties["keyAlias"] as String
            keyPassword = keyStoreProperties["keyPassword"] as String
            storePassword = keyStoreProperties["storePassword"] as String
            storeFile = file(keyStoreProperties["storeFile"] as String)
        }
    }

    defaultConfig {
        applicationId = "com.neocalc.neocalc"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.system.ui.controller)

    // for calculations on strings input
    implementation(libs.rhino.android)

    implementation(libs.room.runtime)
    implementation(libs.room.coroutine)
    implementation(libs.room.paging)
    annotationProcessor(libs.room.annotation.processor)
    ksp(libs.room.annotation.processor)
    implementation(libs.hilt)
    ksp(libs.hilt.annotation.processor)
    implementation(libs.hilt.navigation)
    // paging 3
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    // compose Spin kit Loader
    implementation(libs.msz.progress.indicator)
    // datastore
    implementation(libs.datastore)
    // gson
    implementation(libs.gson)
    // compose-lifecycle
    implementation(libs.compose.lifecycle)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

//kapt {
//    correctErrorTypes = true
//}