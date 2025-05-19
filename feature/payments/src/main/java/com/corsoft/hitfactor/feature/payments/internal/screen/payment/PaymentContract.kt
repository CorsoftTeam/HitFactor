package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
internal data class PaymentScreenModel(
    val error: String = "",
    val isLoading: Boolean = false,
) : MviState

internal sealed interface PaymentAction : MviAction {
    data object Pay : PaymentAction
}

internal sealed interface PaymentEffect : MviEffect {
    data object GoNext : PaymentEffect
    data class ShowError(val message: String) : PaymentEffect
}