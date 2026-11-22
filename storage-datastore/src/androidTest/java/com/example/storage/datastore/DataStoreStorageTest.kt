package com.example.storage.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storage.core.StorageKey
import com.example.storage.core.StorageTypeMismatchException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataStoreStorageTest {

    private lateinit var storage: DataStoreStorage
    private lateinit var dataStore: androidx.datastore.core.DataStore<
            androidx.datastore.preferences.core.Preferences
            >

    private val testFileName = "storage_sdk_test"

    private val usernameKey =
        StorageKey("username", String::class)

    private val ageKey =
        StorageKey("age", Int::class)

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        dataStore = DataStoreProvider.getDataStore(
            context = context,
            fileName = testFileName
        )

        // Start each test with an empty file.
        dataStore.edit { preferences ->
            preferences.clear()
        }

        storage = DataStoreStorage(dataStore)
    }

    @After
    fun tearDown(): Unit = runBlocking {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    @Test
    fun putAndGetString() = runBlocking {
        storage.put(usernameKey, "Asha")

        assertEquals("Asha", storage.get(usernameKey))
    }

    @Test
    fun getMissingKeyReturnsNull() = runBlocking {
        assertNull(storage.get(usernameKey))
    }

    @Test
    fun removeDeletesOnlyRequestedKey() = runBlocking {
        storage.put(usernameKey, "Asha")
        storage.put(ageKey, 24)

        storage.remove(usernameKey)

        assertNull(storage.get(usernameKey))
        assertEquals(24, storage.get(ageKey))
    }

    @Test
    fun clearRemovesAllKeys() = runBlocking {
        storage.put(usernameKey, "Asha")
        storage.put(ageKey, 24)

        storage.clear()

        assertNull(storage.get(usernameKey))
        assertNull(storage.get(ageKey))
    }

    @Test
    fun getWithWrongTypeThrowsException() = runBlocking {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("age")] = "twenty-four"
        }

        try {
            storage.get(ageKey)
            throw AssertionError("Expected StorageTypeMismatchException")
        } catch (exception: StorageTypeMismatchException) {
            assertEquals("age", exception.keyName)
        }
    }

    @Test
    fun putAndGetPrimitiveTypes() = runBlocking {
        val enabledKey = StorageKey("enabled", Boolean::class)
        val ageKey = StorageKey("age", Int::class)
        val timestampKey = StorageKey("timestamp", Long::class)
        val scoreKey = StorageKey("score", Float::class)

        storage.put(enabledKey, true)
        storage.put(ageKey, 25)
        storage.put(timestampKey, 123456789L)
        storage.put(scoreKey, 95.5f)

        assertEquals(true, storage.get(enabledKey))
        assertEquals(25, storage.get(ageKey))
        assertEquals(123456789L, storage.get(timestampKey))
        assertEquals(95.5f, storage.get(scoreKey))
    }
}