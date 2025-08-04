plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.yourapp" // Your app's namespace
    compileSdk = 34 // Or your app's compile SDK

    defaultConfig {
        applicationId = "com.example.yourapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // THIS IS THE BLOCK TO MODIFY
    buildTypes {
        getByName("release") {
            // This is the crucial line you need to add or change.
            // It tells Gradle to sign the 'release' build with the 'debug' key.
            signingConfig = signingConfigs.getByName("debug")

            // You can keep or remove these lines depending on your needs.
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // Other configurations like compileOptions, kotlinOptions, etc. might follow
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Your app's dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    // ... etc.
}
