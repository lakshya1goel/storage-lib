plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.storage.datastore"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    api(project(":storage-core"))
    implementation(libs.androidx.datastore.preferences)
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation(libs.androidx.junit)
}