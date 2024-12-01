package com.corsoft.auth.internal.screen.register

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
internal data class RegisterScreenState(
    val login: String = "",
    val password: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val error: String = ""
) : MviState

internal sealed interface RegisterAction : MviAction {
    data object Register : RegisterAction
    data class UpdateLogin(val login: String) : RegisterAction
    data class UpdatePassword(val password: String) : RegisterAction
    data class UpdateName(val name: String) : RegisterAction
    data class UpdatePhone(val phone: String) : RegisterAction
    data class UpdateEmail(val email: String) : RegisterAction
}

internal sealed interface RegisterEffect : MviEffect {
    data object Register : RegisterEffect
    data class ShowError(val message: String) : RegisterEffect
}