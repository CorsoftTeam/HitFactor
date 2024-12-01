package com.corsoft.data.storage

import android.content.SharedPreferences
import ppk.app.core.common.delegate.StringPrefsDelegate

interface EncryptedStorage {
    var accessToken: String?
}

internal class EncryptedStorageImpl(preferences: SharedPreferences) : EncryptedStorage {

    override var accessToken: String? by StringPrefsDelegate(
        preferences = preferences,
        key = "access_token",
    )
}