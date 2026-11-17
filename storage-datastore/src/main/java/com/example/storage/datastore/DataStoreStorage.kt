package com.example.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.storage.core.Storage
import com.example.storage.core.StorageKey
import com.example.storage.core.StorageTypeMismatchException
import kotlinx.coroutines.flow.first

class DataStoreStorage(
    dataStore: DataStore<Preferences>
) : Storage {
    private val dataStore = dataStore

    override suspend fun <T : Any> put(
        key: StorageKey<T>,
        value: T
    ) {
        if (value::class != key.valueClass) {
            throw StorageTypeMismatchException(
                key.name,
                key.valueClass,
                value::class
            )
        }

        when (value) {
            is String, is Int, is Boolean, is Long, is Float -> Unit
            else -> throw IllegalArgumentException(
                "Unsupported storage type: ${value::class.simpleName}"
            )
        }

        dataStore.edit { preferences ->

            val existingEntry = preferences.asMap()
                .entries
                .firstOrNull { it.key.name == key.name }

            if (existingEntry != null &&
                existingEntry.value::class != key.valueClass
            ) {
                throw StorageTypeMismatchException(
                    key.name,
                    key.valueClass,
                    existingEntry.value::class
                )
            }

            when (value) {
                is String ->
                    preferences[stringPreferencesKey(key.name)] = value

                is Int ->
                    preferences[intPreferencesKey(key.name)] = value

                is Boolean ->
                    preferences[booleanPreferencesKey(key.name)] = value

                is Long ->
                    preferences[longPreferencesKey(key.name)] = value

                is Float ->
                    preferences[floatPreferencesKey(key.name)] = value
            }
        }
    }

    override suspend fun <T : Any> get(
        key: StorageKey<T>
    ): T? {
        val preferences = dataStore.data.first()

        val storedValue = preferences.asMap()
            .entries
            .firstOrNull { it.key.name == key.name }
            ?.value
            ?: return null

        if (storedValue::class != key.valueClass) {
            throw StorageTypeMismatchException(
                key.name,
                key.valueClass,
                storedValue::class
            )
        }

        @Suppress("UNCHECKED_CAST")
        return storedValue as T
    }

    override suspend fun remove(key: StorageKey<*>) {
        dataStore.edit { preferences ->

            val existingEntry = preferences.asMap()
                .entries
                .firstOrNull { it.key.name == key.name }

            if (existingEntry != null) {
                if (existingEntry.value::class != key.valueClass) {
                    throw StorageTypeMismatchException(
                        key.name,
                        key.valueClass,
                        existingEntry.value::class
                    )
                }

                preferences.remove(existingEntry.key)
            }
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
