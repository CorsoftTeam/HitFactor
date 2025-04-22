package com.corsoft.services.internal.screen.service_list

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.ServiceModel

@Immutable
internal data class ServiceListScreenState(
    val serviceList: List<ServiceModel> = emptyList()
) : MviState

internal sealed interface ServiceListAction : MviAction

internal sealed interface ServiceListEffect : MviEffect