package com.corsoft.hitfactor.data.user.internal.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.corsoft.data.api.database.BaseDao
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GunDocumentsDao : BaseDao<GunDocumentsEntity> {
    @Query("SELECT * FROM gun_documents WHERE gunId = :id")
    override suspend fun getById(id: String): GunDocumentsEntity?

    @Query("SELECT * FROM gun_documents")
    override suspend fun observeAll(): List<GunDocumentsEntity>
}