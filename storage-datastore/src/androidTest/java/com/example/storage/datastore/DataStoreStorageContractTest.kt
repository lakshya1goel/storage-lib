package com.example.storage.datastore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storage.core.Storage
import com.example.storage.testing.StorageContractTest
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class DataStoreStorageContractTest : StorageContractTest() {

    override fun createStorage(): Storage {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        return DataStoreStorage(
            DataStoreProvider.getDataStore(
                context,
                "contract_test"
            )
        )
    }

    @Test
    fun cleanUp() = runBlocking {
        storage.clear()
    }
}