package com.corsoft.services.internal.screen.profile

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.ServiceModel

@Immutable
internal data class ProfileScreenState(
    val serviceList: List<ServiceModel> = emptyList(),
    val isLoading: Boolean = true
) : MviState

internal sealed interface ProfileAction : MviAction

internal sealed interface ProfileEffect : MviEffect