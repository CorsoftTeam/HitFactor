package com.corsoft.services.internal.component.enum

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes

enum class GunTypeEnum {
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

    fun getKey(): String =
        when (this) {
            PISTOL -> "pistol"
            SELF_DEFENCE -> "self_defence"
            PCC -> "pcc"
            CARBINE -> "carbine"
            SHOTGUN -> "shotgun"
            BOLT_ACTION -> "bolt_action"
        }

    fun getImageRes() : Int =
        when (this) {
            SELF_DEFENCE -> CoreDrawableRes.ic_pistol
            PISTOL -> CoreDrawableRes.ic_pistol
            PCC -> CoreDrawableRes.ic_pcc
            CARBINE -> CoreDrawableRes.ic_carbine
            SHOTGUN -> CoreDrawableRes.ic_shotgun
            BOLT_ACTION -> CoreDrawableRes.ic_bolt_action
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