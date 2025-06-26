package com.corsoft.hitfactor.data.user.internal

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.Trainer
import com.corsoft.hitfactor.data.user.api.model.Training
import com.corsoft.hitfactor.data.user.internal.mapper.getCityItem
import com.corsoft.hitfactor.data.user.internal.mapper.getGunItem
import com.corsoft.hitfactor.data.user.internal.mapper.getRangeItem
import com.corsoft.hitfactor.data.user.internal.mapper.getTrainerItem
import com.corsoft.hitfactor.data.user.internal.mapper.getTrainingItem
import com.corsoft.hitfactor.data.user.internal.network.UserApi
import com.corsoft.network.model.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import kotlin.coroutines.resume

internal class UserRepositoryImpl(
    private val userApi: UserApi,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun getMyGuns(): NetworkResponse<List<Gun>> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("guns")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(NetworkResponse.Success(querySnapshot.documents.map {
                        getGunItem(
                            it.data
                        )
                    }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun addGun(
        name: String,
        caliber: String,
        serialNumber: String,
        type: String
    ): NetworkResponse<Unit> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            val newGunRef = firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("guns")
                .document()

            newGunRef
                .set(
                    mapOf(
                        "uid" to newGunRef.id,
                        "name" to name,
                        "caliber" to caliber,
                        "serialNumber" to serialNumber,
                        "gunType" to type,
                        "shotCount" to 0,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                ).addOnCompleteListener { taskRegister ->
                    if (taskRegister.isSuccessful) {
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
        }

    override suspend fun getGunById(id: String): NetworkResponse<Gun> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("guns")
                .document(id)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(NetworkResponse.Success(getGunItem(querySnapshot.data)))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun deleteGunById(id: String): NetworkResponse<Unit> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("guns")
                .document(id)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(NetworkResponse.Success(Unit))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun getCities(): NetworkResponse<List<City>> =
        suspendCancellableCoroutine { continuation ->
            firestore
                .collection("cities")
                .get()
                .addOnSuccessListener { response ->
                    Log.d("CITIES", response.documents.toString())
                    continuation.resume(NetworkResponse.Success(response.documents.map { getCityItem(it.data) }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun getRanges(cityId: String): NetworkResponse<List<Range>> =
        suspendCancellableCoroutine { continuation ->
            firestore
                .collection("cities")
                .document(cityId)
                .collection("ranges")
                .get()
                .addOnSuccessListener {
                    continuation.resume(NetworkResponse.Success(it.documents.map { range -> getRangeItem(range.data) }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTrainings(): NetworkResponse<List<Training>> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("trainings")
                .get()
                .addOnSuccessListener {
                    continuation.resume(NetworkResponse.Success(it.documents.map { range -> getTrainingItem(range.data) }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun addTraining(
        dateTime: LocalDateTime,
        length: Int,
        hfScore: Float,
        note: String,
        weaponId: String,
        shotCount: Int
    ): NetworkResponse<Unit> =
        suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            val newTrainingRef = firestore
                .collection("users")
                .document(user?.uid ?: "")
                .collection("trainings")
                .document()

            newTrainingRef
                .set(
                    mapOf(
                        "uid" to newTrainingRef.id,
                        "dateTime" to dateTime,
                        "length" to length,
                        "hfScore" to hfScore,
                        "note" to note,
                        "weaponId" to weaponId,
                        "shotCount" to shotCount,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                ).addOnCompleteListener { taskRegister ->
                    if (taskRegister.isSuccessful) {
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
        }

    override suspend fun getTrainers(cityId: String): NetworkResponse<List<Trainer>> =
        suspendCancellableCoroutine { continuation ->
            firestore
                .collection("cities")
                .document(cityId)
                .collection("trainers")
                .get()
                .addOnSuccessListener {
                    continuation.resume(NetworkResponse.Success(it.documents.map { range -> getTrainerItem(range.data) }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

}