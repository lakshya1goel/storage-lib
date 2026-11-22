package com.example.storage.sharedpreferences

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storage.core.Storage
import com.example.storage.testing.StorageContractTest
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesStorageContractTest : StorageContractTest() {

    override fun createStorage(): Storage {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        context.getSharedPreferences(
            "contract_test",
            Context.MODE_PRIVATE
        ).edit()
            .clear()
            .commit()

        return SharedPreferencesStorage(
            context,
            "contract_test"
        )
    }
}