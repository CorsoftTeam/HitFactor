package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import kotlinx.coroutines.launch

internal class PaymentViewModel(
    private val paymentsRepository: PaymentsRepository,
) : MviViewModel<PaymentScreenModel, PaymentAction, PaymentEffect>(
    PaymentScreenModel()
) {

    override fun onAction(action: PaymentAction) {
        paymentsRepository.pay {
            checkSub()
        }
    }

    private fun checkSub() {
        viewModelScope.launch {
            paymentsRepository.isSub { result ->
                if (result == true) {
                    sendEffect(PaymentEffect.GoNext)
                }
            }
        }
    }
}