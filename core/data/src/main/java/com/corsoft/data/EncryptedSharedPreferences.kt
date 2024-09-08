package ppk.app.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val SECRET_SHARED_PREFS = "secret_shared_prefs"

internal fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
    return EncryptedSharedPreferences.create(
        SECRET_SHARED_PREFS,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}