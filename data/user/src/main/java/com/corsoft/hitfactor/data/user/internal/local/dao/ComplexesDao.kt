package com.corsoft.hitfactor.data.user.internal.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.corsoft.data.api.database.BaseDao
import com.corsoft.hitfactor.data.user.api.entities.ComplexEntity

@Dao
interface ComplexesDao : BaseDao<ComplexEntity> {
    @Query("SELECT * FROM complexes WHERE id = :id")
    override suspend fun getById(id: String): ComplexEntity?

    @Query("SELECT * FROM complexes")
    override suspend fun observeAll(): List<ComplexEntity>

    @Query("DELETE FROM complexes WHERE id = :id")
    suspend fun deleteById(id: String)
}