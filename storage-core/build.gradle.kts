plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.storage.core"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }
}
