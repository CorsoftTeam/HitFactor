package com.corsoft.hitfactor.data.user.api

import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.network.model.NetworkResponse

interface UserRepository {
    suspend fun getMyGuns(): NetworkResponse<List<Gun>>
    suspend fun addGun(
        name: String,
        caliber: String,
        serialNumber: String,
        type: String
    ): NetworkResponse<Unit>
    suspend fun getGunById( id: String): NetworkResponse<Gun>
    suspend fun deleteGunById( id: String): NetworkResponse<Unit>
    suspend fun getCities(): NetworkResponse<List<City>>
    suspend fun getRanges(cityId: String): NetworkResponse<List<Range>>
}