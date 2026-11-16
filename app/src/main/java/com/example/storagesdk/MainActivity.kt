package com.example.storagesdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storage.core.StorageKey
import com.example.storage.sharedpreferences.SharedPreferencesStorage
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var storage: SharedPreferencesStorage

    private val usernameKey =
        StorageKey("username", String::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage = SharedPreferencesStorage(applicationContext)

        lifecycleScope.launch {
            storage.put(usernameKey, "Lakshya")

            val username = storage.get(usernameKey)

            println("Stored username: $username")

            storage.remove(usernameKey)

            val removedUsername = storage.get(usernameKey)

            println("After removal: $removedUsername")
        }
    }
}
