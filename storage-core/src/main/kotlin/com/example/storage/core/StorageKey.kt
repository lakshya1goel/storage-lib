package com.example.storage.core

import kotlin.reflect.KClass

data class StorageKey<T : Any>(
    val name: String,
    val valueClass: KClass<T>
)
