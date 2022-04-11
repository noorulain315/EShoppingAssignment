package com.example.eshoppingassignment.util

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<T> {
    data class ResourceSuccess<T>(val data: T) : Resource<T>()
    data class ResourceError<T>(val error: Throwable) : Resource<T>()
    data class ResourceLoading<T>(val data: T? = null) : Resource<T>()
}