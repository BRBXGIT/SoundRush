plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Compose
    alias(libs.plugins.kotlin.compose)
    // Hilt
    alias(libs.plugins.hilt.android)
    // Ksp
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.common"
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

    // Core modules
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    // Paging
    implementation(libs.androidx.paging.compose)
}