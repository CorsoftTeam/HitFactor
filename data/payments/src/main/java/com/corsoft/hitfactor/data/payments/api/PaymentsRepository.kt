package com.corsoft.hitfactor.data.payments.api

import com.corsoft.network.model.NetworkResponse

interface PaymentsRepository {
    fun pay(onDone: () -> Unit)
    suspend fun isSub(): Boolean?
    suspend fun isAuth(): Boolean?
    suspend fun checkCode(code: String? = null): NetworkResponse<Boolean>
}