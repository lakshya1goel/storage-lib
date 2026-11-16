package com.example.storage.sharedpreferences

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storage.core.StorageKey
import com.example.storage.core.StorageTypeMismatchException
package com.example.storage.sharedpreferences

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storage.core.StorageKey
import com.example.storage.core.StorageTypeMismatchException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesStorageTest {

    private lateinit var storage: SharedPreferencesStorage
    private lateinit var context: Context

    private val preferencesName = "storage_sdk_test"

    private val USERNAME =
        StorageKey("username", String::class)

    private val AGE =
        StorageKey("age", Int::class)

    @Before
    fun setUp() {
        context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .clear()
            .commit()

        storage = SharedPreferencesStorage(
            context,
            preferencesName
        )
    }

    @After
    fun tearDown() {
        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .clear()
            .commit()
    }

    @Test
    fun putAndGetString() = runBlocking {
        storage.put(USERNAME, "Lakshya")

        val result = storage.get(USERNAME)

        assertEquals("Lakshya", result)
    }

    @Test
    fun getMissingKeyReturnsNull() = runBlocking {
        val result = storage.get(USERNAME)

        assertNull(result)
    }

    @Test
    fun removeDeletesOnlyRequestedKey() = runBlocking {
        storage.put(USERNAME, "Lakshya")
        storage.put(AGE, 24)

        storage.remove(USERNAME)

        assertNull(storage.get(USERNAME))
        assertEquals(24, storage.get(AGE))
    }

    @Test
    fun clearRemovesAllKeys() = runBlocking {
        storage.put(USERNAME, "Lakshya")
        storage.put(AGE, 24)

        storage.clear()

        assertNull(storage.get(USERNAME))
        assertNull(storage.get(AGE))
    }

    @Test
    fun getWithWrongTypeThrowsException() = runBlocking {
        // Bypass our SDK and directly insert an incorrect value.
        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .putString("age", "twenty-four")
            .commit()

        try {
            storage.get(AGE)
            fail("Expected StorageTypeMismatchException")
        } catch (exception: StorageTypeMismatchException) {
            assertEquals("age", exception.keyName)
        }
    }
}

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesStorageTest {

    private lateinit var storage: SharedPreferencesStorage
    private lateinit var context: Context

    private val preferencesName = "storage_sdk_test"

    private val USERNAME =
        StorageKey("username", String::class)

    private val AGE =
        StorageKey("age", Int::class)

    @Before
    fun setUp() {
        context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .clear()
            .commit()

        storage = SharedPreferencesStorage(
            context,
            preferencesName
        )
    }

    @After
    fun tearDown() {
        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .clear()
            .commit()
    }

    @Test
    fun putAndGetString() = runBlocking {
        storage.put(USERNAME, "Lakshya")

        val result = storage.get(USERNAME)

        assertEquals("Lakshya", result)
    }

    @Test
    fun getMissingKeyReturnsNull() = runBlocking {
        val result = storage.get(USERNAME)

        assertNull(result)
    }

    @Test
    fun removeDeletesOnlyRequestedKey() = runBlocking {
        storage.put(USERNAME, "Lakshya")
        storage.put(AGE, 24)

        storage.remove(USERNAME)

        assertNull(storage.get(USERNAME))
        assertEquals(24, storage.get(AGE))
    }

    @Test
    fun clearRemovesAllKeys() = runBlocking {
        storage.put(USERNAME, "Lakshya")
        storage.put(AGE, 24)

        storage.clear()

        assertNull(storage.get(USERNAME))
        assertNull(storage.get(AGE))
    }

    @Test
    fun getWithWrongTypeThrowsException() = runBlocking {
        // Bypass our SDK and directly insert an incorrect value.
        context.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE
        ).edit()
            .putString("age", "twenty-four")
            .commit()

        try {
            storage.get(AGE)
            fail("Expected StorageTypeMismatchException")
        } catch (exception: StorageTypeMismatchException) {
            assertEquals("age", exception.keyName)
        }
    }
}
