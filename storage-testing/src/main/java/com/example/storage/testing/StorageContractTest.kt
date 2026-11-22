package com.example.storage.testing

import com.example.storage.core.Storage
import com.example.storage.core.StorageKey
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

abstract class StorageContractTest {

    protected abstract fun createStorage(): Storage

    protected lateinit var storage: Storage

    @Before
    fun setUp() {
        storage = createStorage()
    }

    @Test
    fun putAndGetString() = runBlocking {
        val key = StorageKey("username", String::class)

        storage.put(key, "Lakshya")

        assertEquals("Lakshya", storage.get(key))
    }

    @Test
    fun getMissingKeyReturnsNull() = runBlocking {
        val key = StorageKey("missing", String::class)

        assertNull(storage.get(key))
    }
}