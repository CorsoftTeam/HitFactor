package com.corsoft.hitfactor.data.payments.internal

import com.corsoft.data.api.storage.EncryptedStorage
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.network.model.NetworkResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.ProductType
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState
import kotlin.coroutines.resume

class PaymentsRepositoryImpl(
    private val client: RuStoreBillingClient,
    private val firestore: FirebaseFirestore,
    private val encryptedStorage: EncryptedStorage
) : PaymentsRepository {

    override fun pay(onDone: () -> Unit) {
        client.purchases.purchaseProduct("monthly_subscription_1").addOnSuccessListener {
            onDone()
        }
    }

    override suspend fun isSub(): Boolean? =
        suspendCancellableCoroutine { continuation ->
            client.purchases.getPurchases()
                .addOnSuccessListener { purchases: List<Purchase> ->
                    val hasActiveSubscription = purchases.any { purchase ->
                        purchase.productType == ProductType.SUBSCRIPTION && purchase.purchaseState == PurchaseState.CONFIRMED
                    }
                    encryptedStorage.isSub = hasActiveSubscription
                    continuation.resume(hasActiveSubscription)
                }
                .addOnFailureListener {
                    continuation.resume(encryptedStorage.isSub)
                }
        }

    override suspend fun isAuth(): Boolean? =
        suspendCancellableCoroutine { continuation ->
            client.userInfo.getAuthorizationStatus().addOnSuccessListener {
                continuation.resume(it.authorized)
            }.addOnFailureListener {
                continuation.resume(null)
            }
        }

    override suspend fun checkCode(code: String?): NetworkResponse<Boolean> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection("codes").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val codes = task.result.documents
                        var resultResumed = false

                        fun resumeIfNotCompleted(value: NetworkResponse<Boolean>) {
                            if (!resultResumed) {
                                resultResumed = true
                                continuation.resume(value)
                            }
                        }

                        if (code == null) {
                            if (encryptedStorage.promocode.isNullOrBlank()) {
                                encryptedStorage.isSub = false
                                resumeIfNotCompleted(NetworkResponse.Success(false))
                            } else {
                                for (existingCode in codes) {
                                    if (existingCode.id == encryptedStorage.promocode) {
                                        encryptedStorage.isSub = true
                                        resumeIfNotCompleted(NetworkResponse.Success(true))
                                        return@addOnCompleteListener
                                    }
                                }
                                encryptedStorage.isSub = false
                                resumeIfNotCompleted(NetworkResponse.Success(false))
                            }
                        } else {
                            for (existingCode in codes) {
                                if (existingCode.id == code) {
                                    if (!(existingCode.data?.get("isUsed") as Boolean)) {
                                        encryptedStorage.promocode = code
                                        firestore.collection("codes").document(code).set(
                                            mapOf(
                                                "isUsed" to true
                                            )
                                        )
                                        encryptedStorage.isSub = true
                                        resumeIfNotCompleted(NetworkResponse.Success(true))
                                        return@addOnCompleteListener
                                    }
                                }
                            }
                            encryptedStorage.isSub = false
                            resumeIfNotCompleted(NetworkResponse.Success(false))
                        }

                    } else {
                        continuation.resume(
                            NetworkResponse.Success(encryptedStorage.isSub)
                        )
                    }
                }
        }
}