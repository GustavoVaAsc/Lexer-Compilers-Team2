android {
    compileSdk = 34
    namespace = "unam.fi.compilers.lexer"
    defaultConfig {
        applicationId = "unam.fi.compilers.lexer"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").manifest.srcFile("app/src/main/AndroidManifest.xml")
    }

}

plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.kotlin.android) apply true
    alias(libs.plugins.kotlin.compose) apply true
}

dependencies {
    implementation(libs.androidx.appcompat)  // Correct key from TOML for AppCompat
    implementation(libs.material)           // Correct key from TOML for Material Design
}

buildscript {
    dependencies {
        classpath(libs.gradle)  // Make sure this matches your AGP version
    }
}


