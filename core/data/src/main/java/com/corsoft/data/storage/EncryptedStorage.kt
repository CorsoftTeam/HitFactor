package com.corsoft.data.storage

import android.content.SharedPreferences
import com.corsoft.common.delegate.StringPrefsDelegate

interface EncryptedStorage {
    var accessToken: String?
    var cookie: String?
}

internal class EncryptedStorageImpl(preferences: SharedPreferences) : EncryptedStorage {

    override var accessToken: String? by StringPrefsDelegate(
        preferences = preferences,
        key = "access_token",
    )

    override var cookie: String? by StringPrefsDelegate(
        preferences = preferences,
        key = "cookie",
    )
}