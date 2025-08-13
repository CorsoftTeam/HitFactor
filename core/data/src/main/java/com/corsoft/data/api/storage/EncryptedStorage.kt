package com.corsoft.data.api.storage

import android.content.SharedPreferences
import com.corsoft.common.delegate.StringPrefsDelegate

interface EncryptedStorage {
    var accessToken: String?
    var cookie: String?
    var promocode: String?
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

    override var promocode: String? by StringPrefsDelegate(
        preferences = preferences,
        key = "promocode",
    )
}