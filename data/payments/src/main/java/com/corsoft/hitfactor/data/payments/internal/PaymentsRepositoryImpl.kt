package com.corsoft.hitfactor.data.payments.internal

import android.util.Log
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.ProductType
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState

class PaymentsRepositoryImpl(
    private val client: RuStoreBillingClient
) : PaymentsRepository {

    override fun pay(onDone: () -> Unit) {
        client.purchases.purchaseProduct("monthly_subscription_1").addOnSuccessListener {
            onDone()
        }
    }

    override suspend fun isSub(onDone: (Boolean?) -> Unit) {
        client.purchases.getPurchases()
            .addOnSuccessListener { purchases: List<Purchase> ->
                Log.d("PAYMENTS", purchases.toString())
                val hasActiveSubscription = purchases.any { purchase ->
                    purchase.productType == ProductType.SUBSCRIPTION && purchase.purchaseState == PurchaseState.CONFIRMED
                }
                onDone(hasActiveSubscription)
            }
            .addOnFailureListener {
                onDone(null)
            }
    }

    override suspend fun isAuth(onDone: (Boolean) -> Unit) {
        client.userInfo.getAuthorizationStatus().addOnSuccessListener {
            onDone(it.authorized)
        }.addOnFailureListener {
            onDone(false)
        }
    }
}