package com.example.storage.core

interface Storage {

    suspend fun <T : Any> put(
        key: StorageKey<T>,
        value: T
    )

    suspend fun <T : Any> get(
        key: StorageKey<T>
    ): T?

    suspend fun remove(
        key: StorageKey<*>
    )

    suspend fun clear()
}
