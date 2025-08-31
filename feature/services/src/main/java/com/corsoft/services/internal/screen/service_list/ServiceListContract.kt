package com.corsoft.services.internal.screen.service_list

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.ServicesGroupsEnum
import com.corsoft.services.internal.model.ServiceModel

@Immutable
internal data class ServiceListScreenState(
    val serviceList: List<ServiceModel> = emptyList(),
    val serviceGroup: ServicesGroupsEnum = ServicesGroupsEnum.ALL,
    val isServiceModeGrid: Boolean = false
) : MviState

internal sealed interface ServiceListAction : MviAction {
    data object Refresh: ServiceListAction
    data object ChangeMode: ServiceListAction
}

internal sealed interface ServiceListEffect : MviEffect