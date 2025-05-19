package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository

internal class PaymentViewModel(
    private val paymentsRepository: PaymentsRepository
) : MviViewModel<PaymentScreenModel, PaymentAction, PaymentEffect>(
    PaymentScreenModel()
) {

    override fun onAction(action: PaymentAction) {
        paymentsRepository.pay()
    }
}