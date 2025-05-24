plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //Compose
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.design_system"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    //Material 3
    implementation(libs.androidx.material3)
}