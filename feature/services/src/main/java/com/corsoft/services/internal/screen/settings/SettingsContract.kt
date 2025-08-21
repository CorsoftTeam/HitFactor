package com.corsoft.services.internal.screen.settings

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.ServicesGroupsEnum

@Immutable
internal data class SettingsScreenState(
    val serviceGroup: ServicesGroupsEnum = ServicesGroupsEnum.ALL
) : MviState

internal sealed interface SettingsAction : MviAction {
    data class OnServiceGroupChange(val name: String) : SettingsAction
}

internal sealed interface SettingsEffect : MviEffect