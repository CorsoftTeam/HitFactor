package com.corsoft.hitfactor.data.payments.api

interface PaymentsRepository {
    fun pay(onDone: () -> Unit)
    suspend fun isSub(onDone: (Boolean?) -> Unit)
    suspend fun isAuth(onDone: (Boolean) -> Unit)
}