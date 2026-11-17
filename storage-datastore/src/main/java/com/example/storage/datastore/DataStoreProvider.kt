package com.example.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object DataStoreProvider {
    private val instances =
        mutableMapOf<String, DataStore<Preferences>>()

    fun getDataStore(
        context: Context,
        fileName: String = "storage_sdk"
    ): DataStore<Preferences> {

        val appContext = context.applicationContext

        return synchronized(instances) {
            instances.getOrPut(fileName) {
                PreferenceDataStoreFactory.create(
                    scope = CoroutineScope(
                        SupervisorJob() + Dispatchers.IO
                    ),
                    produceFile = {
                        appContext.preferencesDataStoreFile(fileName)
                    }
                )
            }
        }
    }
}
