package com.corsoft.services.internal.screen.profile

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.ServiceModel
import com.corsoft.services.internal.model.UserModel

@Immutable
internal data class ProfileScreenState(
    val user: UserModel = UserModel(),
    val profilePhoto: String = "",
    val serviceList: List<ServiceModel> = emptyList(),
    val isLoading: Boolean = true
) : MviState

internal sealed interface ProfileAction : MviAction {
    data object OnExit: ProfileAction
}

internal sealed interface ProfileEffect : MviEffect {
    data class ShowError(val message: String) : ProfileEffect
    data object Logout: ProfileEffect
}