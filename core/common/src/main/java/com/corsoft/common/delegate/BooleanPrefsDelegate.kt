package com.corsoft.common.delegate

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPrefsDelegate(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean = false,
) : ReadWriteProperty<Any?, Boolean> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }
}