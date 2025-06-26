package com.corsoft.services.internal.screen.weapons

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.GunModel

@Immutable
internal data class WeaponsScreenState(
    val weaponsList: List<GunModel> = emptyList(),
    val isLoading: Boolean = true
) : MviState

internal sealed interface WeaponsAction : MviAction {
    data object Refresh: WeaponsAction
}

internal sealed interface WeaponsEffect : MviEffect