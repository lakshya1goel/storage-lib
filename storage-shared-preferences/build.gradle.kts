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
    }
}

dependencies {
    implementation(project(":storage-core"))
    implementation(libs.androidx.core.ktx)
}