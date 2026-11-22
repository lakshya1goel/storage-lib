plugins {
    id("com.android.library")
}

android {
    namespace = "com.example.storage.testing"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(project(":storage-core"))

    // JUnit is used by StorageContractTest itself.
    api(libs.junit)

    // We'll add the existing coroutines dependency here.
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}