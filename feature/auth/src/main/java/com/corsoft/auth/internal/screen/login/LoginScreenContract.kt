package com.corsoft.auth.internal.screen.login

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
internal data class LoginScreenModel(
    val login: String = "",
    val password: String = "",
    val error: String = ""
) : MviState

internal sealed interface LoginAction : MviAction {
    data object Login : LoginAction
    data class UpdateLogin(val login: String) : LoginAction
    data class UpdatePassword(val password: String) : LoginAction

}

internal sealed interface LoginEffect : MviEffect {
    data object Login : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}
