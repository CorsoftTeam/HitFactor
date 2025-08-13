package com.corsoft.data.internal

import android.content.Context
import com.corsoft.data.api.storage.LocalStorage

class LocalStorageImpl(
    context: Context
) : LocalStorage {

    private val sharedPrefs = context.getSharedPreferences("hf_preferences", Context.MODE_PRIVATE)

    override fun addString(key: String, value: String) {
        with(sharedPrefs.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String = sharedPrefs.getString(key, "") ?: ""
}