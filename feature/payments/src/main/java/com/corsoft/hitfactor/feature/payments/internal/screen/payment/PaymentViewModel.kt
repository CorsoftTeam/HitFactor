package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import androidx.lifecycle.viewModelScope
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.resources.CoreStringRes
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class PaymentViewModel(
    private val paymentsRepository: PaymentsRepository,
    private val resourceProvider: ResourceProvider
) : MviViewModel<PaymentScreenModel, PaymentAction, PaymentEffect>(
    PaymentScreenModel()
) {

    override fun onAction(action: PaymentAction) {
        when (action) {
            is PaymentAction.CheckCode -> checkCode(action.code)
            PaymentAction.Pay -> pay()
        }
    }

    private fun checkCode(code: String? = null) {
        viewModelScope.launch {
            paymentsRepository.checkCode(code).doOn(
                success = { response ->
                    if (response) {
                        sendEffect(PaymentEffect.GoNext)
                    } else {
                        if (code != null) {
                            sendEffect(
                                PaymentEffect.ShowError(
                                    resourceProvider.getString(
                                        CoreStringRes.wrong_promocode
                                    )
                                )
                            )
                        }
                        changeState {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                },
                failed = {
                    changeState {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    private fun pay() {
        paymentsRepository.pay {
            checkSub()
        }
    }

    private fun checkSub() {
        viewModelScope.launch {
            if (paymentsRepository.isSub() == true) {
                sendEffect(PaymentEffect.GoNext)
            } else {
                checkCode()
            }
        }
    }

    init {
        checkSub()
    }
}