package com.corsoft.auth.internal

import com.corsoft.auth.api.AuthRepository
import com.corsoft.network.model.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(email: String, password: String): NetworkResponse<Unit> =
        suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(NetworkResponse.Success(Unit))
                } else {
                    continuation.resume(
                        NetworkResponse.Failed(
                            Throwable(
                                task.exception?.message ?: "Неизвестная ошибка"
                            )
                        )
                    )
                }
            }
        }

    override suspend fun register(
        login: String,
        password: String,
        email: String,
        name: String
    ): NetworkResponse<Unit> =
        suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    firestore.collection("users").document(user?.uid ?: "").set(mapOf(
                        "uid" to user?.uid,
                        "name" to name,
                        "login" to login,
                        "email" to email,
                        "createdAt" to FieldValue.serverTimestamp()
                    )).addOnCompleteListener { taskRegister ->
                        if (taskRegister.isSuccessful){
                            continuation.resume(NetworkResponse.Success(Unit))
                        } else {
                            continuation.resume(
                                NetworkResponse.Failed(
                                    Throwable(
                                        taskRegister.exception?.message ?: "Неизвестная ошибка"
                                    )
                                )
                            )
                        }
                    }
                } else {
                    continuation.resume(
                        NetworkResponse.Failed(
                            Throwable(
                                task.exception?.message ?: "Неизвестная ошибка"
                            )
                        )
                    )
                }
            }
        }

    override suspend fun isUserAuthorised(): Boolean =
        suspendCancellableCoroutine { continuation ->
            val currentUser = auth.currentUser
            if (currentUser != null) {
                continuation.resume(true)
            } else {
                continuation.resume(false)
            }
        }

}