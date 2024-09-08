package ppk.app.core.common.delegate

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPrefsDelegate(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: String? = null,
) : ReadWriteProperty<Any?, String?> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return preferences.getString(key, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        preferences.edit().putString(key, value).apply()
    }
}