package com.corsoft.services.internal.screen.weapon_details

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.model.UserModel

@Immutable
internal data class WeaponDetailsScreenState(
    val gunModel: GunModel = GunModel(),
    val isLoading: Boolean = true
) : MviState

internal sealed interface WeaponDetailsAction : MviAction

internal sealed interface WeaponDetailsEffect : MviEffect