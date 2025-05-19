package com.corsoft.hitfactor.feature.payments.di

import com.corsoft.hitfactor.feature.payments.internal.screen.payment.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val paymentsFeatureModule = module {
    viewModelOf(::PaymentViewModel)
}