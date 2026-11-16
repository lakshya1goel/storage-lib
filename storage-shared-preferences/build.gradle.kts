plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.storage.sharedpreferences"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":storage-core"))
    implementation(libs.androidx.core.ktx)

    // Instrumentation testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test:runner:1.7.0")

    // To execute suspend functions in tests
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}