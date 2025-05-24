plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //Compose
    alias(libs.plugins.kotlin.compose)
    //Nav
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.soundrush"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.soundrush"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    //Feature modules
    implementation(project(":feature:navbar-screens"))
    implementation(project(":feature:composition-screen"))
    implementation(project(":feature:playlist-screen"))
    implementation(project(":feature:onboarding-screen"))

    //Nav
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Basic libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}