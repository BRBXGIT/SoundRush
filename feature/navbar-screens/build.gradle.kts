plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Compose
    alias(libs.plugins.kotlin.compose)
    // Nav
    alias(libs.plugins.kotlin.serialization)
    // Ksp
    alias(libs.plugins.ksp)
    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.navbar_screens"
    compileSdk = 35

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

    // Core modules
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    // Feature modules
    implementation(project(":feature:common"))
    implementation(project(":feature:playlist-screen"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    // Material 3
    implementation(libs.androidx.material3)
    // Nav
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Paging
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    // Json
    implementation(libs.converter.gson)
    // Animated icons(compose animation graphics)
    implementation(libs.androidx.animation.graphics)
}