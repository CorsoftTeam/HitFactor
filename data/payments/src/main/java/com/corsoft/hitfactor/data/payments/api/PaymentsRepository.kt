package com.corsoft.hitfactor.data.payments.api

interface PaymentsRepository {
    fun pay()
    fun isSub(): Boolean
}