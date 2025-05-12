package com.corsoft.services.internal.component.enum

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreStringRes

internal enum class GunTypeEnum {
    SELF_DEFENCE,
    PISTOL,
    PCC,
    CARBINE,
    SHOTGUN,
    BOLT_ACTION,
    ;

    @Composable
    fun getName(): String =
        when (this) {
            PISTOL -> stringResource(id = CoreStringRes.pistol)
            SELF_DEFENCE -> stringResource(id = CoreStringRes.self_defence)
            PCC -> stringResource(id = CoreStringRes.pcc)
            CARBINE -> stringResource(id = CoreStringRes.carbine)
            SHOTGUN -> stringResource(id = CoreStringRes.shotgun)
            BOLT_ACTION -> stringResource(id = CoreStringRes.bolt_action)
        }

    companion object {
        fun fromKey(key: String): GunTypeEnum =
            when (key) {
                "self_defence" -> SELF_DEFENCE
                "pistol" -> PISTOL
                "pcc" -> PCC
                "carbine" -> CARBINE
                "shotgun" -> SHOTGUN
                "bolt_action" -> BOLT_ACTION

                else -> PISTOL
            }

        fun fromName(key: String): GunTypeEnum =
            when (key) {
                "ОООП" -> SELF_DEFENCE
                "Пистолет" -> PISTOL
                "КПК" -> PCC
                "Карабин" -> CARBINE
                "Ружье" -> SHOTGUN
                "Болтовка" -> BOLT_ACTION

                else -> PISTOL
            }
    }
}