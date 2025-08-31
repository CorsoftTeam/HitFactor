package com.corsoft.hitfactor.data.user.api

import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.Trainer
import com.corsoft.hitfactor.data.user.api.model.Training
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.network.model.NetworkResponse
import java.time.LocalDateTime

interface UserRepository {
    suspend fun getMe(): NetworkResponse<User>
    suspend fun getMyGuns(): NetworkResponse<List<Gun>>
    suspend fun addGun(
        name: String,
        caliber: String,
        serialNumber: String,
        type: String,
        shotsBeforeClean: Int
    ): NetworkResponse<Unit>

    suspend fun getGunById(id: String): NetworkResponse<Gun>
    suspend fun deleteGunById(id: String): NetworkResponse<Unit>
    suspend fun updateShotCount(gunId: String, addShots: Int): NetworkResponse<Unit>
    suspend fun getCities(): NetworkResponse<List<City>>
    suspend fun getRanges(cityId: String): NetworkResponse<List<Range>>
    suspend fun getTrainings(): NetworkResponse<List<Training>>
    suspend fun getTrainingById(id: String): NetworkResponse<Training>
    suspend fun addTraining(
        dateTime: LocalDateTime,
        length: Int,
        weaponId: String = "",
    ): NetworkResponse<Unit>
    suspend fun completeTraining(
        id: String,
        hfScore: Float,
        note: String,
        shotCount: Int
    ): NetworkResponse<Unit>
    suspend fun getTrainers(cityId: String): NetworkResponse<List<Trainer>>
    suspend fun setGunDocPhotoUrl(
        gunId: String,
        docFirstPageUri: String,
        docSecondPageUri: String,
        licenseFirstPageUri: String,
        licenseSecondPageUri: String,
    )
    suspend fun getGunDocs(gunId: String): GunDocumentsEntity?

    suspend fun updateTraining(
        id: String,
        dateTime: String? = null,
        length: Int? = null,
        hfScore: Float? = null,
        note: String? = null,
        weaponId: String? = null,
        shotCount: Int? = null
    )

    suspend fun deleteTrainingById(id: String): NetworkResponse<Unit>

    suspend fun cleanGunById(id: String)

    suspend fun setTimerSensitivity(sensitivity: Int)

    suspend fun getTimerSensitivity(): Int

    suspend fun addResult(
        name: String,
        score: Int,
        time: Long,
        hitFactor: Float
    )

    suspend fun getResults(): List<ResultEntity>

    suspend fun deleteResultById(id: Long)

    suspend fun setServiceGroup(name: String)

    suspend fun getServiceGroup(): String

    suspend fun setServiceMode(isGrid: Boolean)

    suspend fun getServiceMode(): Boolean
}