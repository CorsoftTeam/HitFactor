package com.corsoft.hitfactor.data.payments.internal

import android.util.Log
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import ru.rustore.sdk.billingclient.RuStoreBillingClient

class PaymentsRepositoryImpl(
    private val client: RuStoreBillingClient
) : PaymentsRepository {

    override fun pay() {
        client.purchases.purchaseProduct("monthly_subscription_1").addOnFailureListener {
            Log.d("SUBS", it.message.toString())
        }
    }

    override fun isSub(): Boolean {
        return false
    }
}