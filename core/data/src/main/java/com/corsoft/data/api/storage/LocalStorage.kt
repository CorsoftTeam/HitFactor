package com.corsoft.data.api.storage

interface LocalStorage {
    fun addString(key: String, value: String)
    fun getString(key: String): String
}