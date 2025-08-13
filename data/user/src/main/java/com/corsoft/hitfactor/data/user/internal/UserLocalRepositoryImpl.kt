package com.corsoft.hitfactor.data.user.internal

import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import com.corsoft.hitfactor.data.user.api.entities.GunEntity
import com.corsoft.hitfactor.data.user.api.entities.TrainingEntity
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.Trainer
import com.corsoft.hitfactor.data.user.api.model.Training
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.hitfactor.data.user.internal.local.db.UserDatabase
import com.corsoft.hitfactor.data.user.internal.mapper.getCityItem
import com.corsoft.hitfactor.data.user.internal.mapper.getRangeItem
import com.corsoft.hitfactor.data.user.internal.mapper.getTrainerItem
import com.corsoft.hitfactor.data.user.internal.mapper.toModel
import com.corsoft.network.model.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import java.util.UUID
import kotlin.coroutines.resume

class UserLocalRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDatabase: UserDatabase
) : UserRepository {
    override suspend fun getMe(): NetworkResponse<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyGuns(): NetworkResponse<List<Gun>> =
        NetworkResponse.Success(userDatabase.gunsDao().observeAll().map { it.toModel() })

    override suspend fun addGun(
        name: String,
        caliber: String,
        serialNumber: String,
        type: String
    ): NetworkResponse<Unit> {
        userDatabase.gunsDao().insert(
            GunEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                caliber = caliber,
                serialNumber = serialNumber,
                gunType = type,
                shotCount = 0
            )
        )
        return NetworkResponse.Success(Unit)
    }

    override suspend fun getGunById(id: String): NetworkResponse<Gun> =
        NetworkResponse.Success(userDatabase.gunsDao().getById(id)?.toModel() ?: Gun())

    override suspend fun deleteGunById(id: String): NetworkResponse<Unit> {
        userDatabase.gunsDao().deleteById(id)
        return NetworkResponse.Success(Unit)
    }

    override suspend fun updateShotCount(
        gunId: String,
        addShots: Int
    ): NetworkResponse<Unit> {
        userDatabase.gunsDao().incrementShotCount(gunId, addShots)
        return NetworkResponse.Success(Unit)
    }

    override suspend fun getCities(): NetworkResponse<List<City>> =
        suspendCancellableCoroutine { continuation ->
            firestore
                .collection("cities")
                .get()
                .addOnSuccessListener { response ->
                    continuation.resume(NetworkResponse.Success(response.documents.map {
                        getCityItem(
                            it.data
                        )
                    }))
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
                    continuation.resume(NetworkResponse.Success(it.documents.map { range ->
                        getRangeItem(
                            range.data
                        )
                    }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun getTrainings(): NetworkResponse<List<Training>> =
        NetworkResponse.Success(userDatabase.trainingsDao().observeAll().map { it.toModel() })

    override suspend fun getTrainingById(id: String): NetworkResponse<Training> =
        NetworkResponse.Success(userDatabase.trainingsDao().getById(id)?.toModel() ?: Training())


    override suspend fun addTraining(
        dateTime: LocalDateTime,
        length: Int,
        weaponId: String
    ): NetworkResponse<Unit> {
        userDatabase.trainingsDao().insert(
            TrainingEntity(
                id = UUID.randomUUID().toString(),
                dateTime = dateTime.toString(),
                length = length,
                weaponId = weaponId
            )
        )
        return NetworkResponse.Success(Unit)
    }

    override suspend fun completeTraining(
        id: String,
        hfScore: Float,
        note: String,
        shotCount: Int
    ): NetworkResponse<Unit> {
        updateTraining(
            id = id,
            hfScore = hfScore,
            note = note,
            shotCount = shotCount
        )
        return NetworkResponse.Success(Unit)
    }

    override suspend fun getTrainers(cityId: String): NetworkResponse<List<Trainer>> =
        suspendCancellableCoroutine { continuation ->
            firestore
                .collection("cities")
                .document(cityId)
                .collection("trainers")
                .get()
                .addOnSuccessListener {
                    continuation.resume(NetworkResponse.Success(it.documents.map { range ->
                        getTrainerItem(
                            range.data
                        )
                    }))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(NetworkResponse.Failed(Throwable(exception.message)))
                }
        }

    override suspend fun setGunDocPhotoUrl(
        gunId: String,
        docFirstPageUri: String,
        docSecondPageUri: String,
        licenseFirstPageUri: String,
        licenseSecondPageUri: String,
    ) {
        userDatabase.gunDocumentsDao().insert(
            GunDocumentsEntity(
                gunId = gunId,
                docFirstPageUri = docFirstPageUri,
                docSecondPageUri = docSecondPageUri,
                licenseFirstPageUri = licenseFirstPageUri,
                licenseSecondPageUri = licenseSecondPageUri
            )
        )
    }

    override suspend fun getGunDocs(gunId: String): GunDocumentsEntity? =
        userDatabase.gunDocumentsDao().getById(gunId)

    override suspend fun updateTraining(
        id: String,
        dateTime: String?,
        length: Int?,
        hfScore: Float?,
        note: String?,
        weaponId: String?,
        shotCount: Int?
    ) {
        userDatabase.trainingsDao().updateTrainingFields(
            id = id,
            dateTime = dateTime,
            length = length,
            hfScore = hfScore,
            note = note,
            weaponId = weaponId,
            shotCount = shotCount
        )
    }
}