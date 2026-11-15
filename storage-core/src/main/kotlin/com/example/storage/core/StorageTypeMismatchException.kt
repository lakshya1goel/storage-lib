package com.example.storage.core

import kotlin.reflect.KClass

class StorageTypeMismatchException(
    val keyName: String,
    val expectedType: KClass<*>,
    val actualType: KClass<*>
) : IllegalStateException(
    "Type mismatch for key '$keyName'. " +
            "Expected ${expectedType.simpleName}, " +
            "but found ${actualType.simpleName}."
)
