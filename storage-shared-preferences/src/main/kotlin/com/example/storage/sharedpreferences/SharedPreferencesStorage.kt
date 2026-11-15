package com.example.storage.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.storage.core.Storage
import com.example.storage.core.StorageKey
import com.example.storage.core.StorageTypeMismatchException

class SharedPreferencesStorage(
    context: Context,
    preferencesName: String = "storage_sdk"
) : Storage {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        )

    override suspend fun <T : Any> put(
        key: StorageKey<T>,
        value: T
    ) {

        val existingValue = preferences.all[key.name]

        if (existingValue != null &&
            existingValue::class != key.valueClass
        ) {
            throw StorageTypeMismatchException(
                key.name,
                key.valueClass,
                existingValue::class
            )
        }

        if (value::class != key.valueClass) {
            throw StorageTypeMismatchException(
                key.name,
                key.valueClass,
                value::class
            )
        }

        preferences.edit {
            when (value) {
                is String -> putString(key.name, value)
                is Int -> putInt(key.name, value)
                is Boolean -> putBoolean(key.name, value)
                is Long -> putLong(key.name, value)
                is Float -> putFloat(key.name, value)

                else -> throw IllegalArgumentException(
                    "Unsupported storage type: ${value::class.simpleName}"
                )
            }
        }
    }

    override suspend fun <T : Any> get(
        key: StorageKey<T>
    ): T? {

        val storedValue = preferences.all[key.name] ?: return null

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

    override suspend fun remove(
        key: StorageKey<*>
    ) {
        val existingValue = preferences.all[key.name]

        if (existingValue != null &&
            existingValue::class != key.valueClass
        ) {
            throw StorageTypeMismatchException(
                key.name,
                key.valueClass,
                existingValue::class
            )
        }

        preferences.edit {
            remove(key.name)
        }
    }

    override suspend fun clear() {
        preferences.edit {
            clear()
        }
    }
}
