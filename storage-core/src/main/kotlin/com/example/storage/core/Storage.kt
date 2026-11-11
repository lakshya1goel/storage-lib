package com.example.storage.core

interface Storage {

    suspend fun <T> put(
        key: StorageKey<T>,
        value: T
    )

    suspend fun <T> get(
        key: StorageKey<T>
    ): T?

    suspend fun remove(
        key: StorageKey<*>
    )

    suspend fun clear()
}
