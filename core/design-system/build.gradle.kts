plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Compose
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.design_system"
    compileSdk = 36

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    // Material 3
    implementation(libs.androidx.material3.android)
    // Coil
    implementation(libs.coil.compose)
    // Android testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}