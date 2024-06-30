plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // For Firebase
    id("com.google.gms.google-services")

    // for Parcelization
    id("kotlin-parcelize")

    // Secrets
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

// Compose Destinations
kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.example.agri_link"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.agri_link"
        minSdk = 27
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

        // added for Google Places
        buildConfig = true
    }
    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // was 1.5.1
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
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    // Accompanist Permissions Library
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil image loading library for Android
    implementation(libs.coil.compose)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // Google Places
    implementation(platform(libs.kotlin.bom))
    implementation(libs.places)
    // Google Maps
    implementation(libs.maps.compose)
    // Maps SDK for Android
    implementation (libs.play.services.maps)

}