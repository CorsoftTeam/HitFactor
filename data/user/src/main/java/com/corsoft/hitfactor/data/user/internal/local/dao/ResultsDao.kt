package com.corsoft.hitfactor.data.user.internal.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.corsoft.data.api.database.BaseDao
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity

@Dao
interface ResultsDao : BaseDao<ResultEntity> {
    @Query("SELECT * FROM results WHERE id = :id")
    override suspend fun getById(id: String): ResultEntity?

    @Query("SELECT * FROM results")
    override suspend fun observeAll(): List<ResultEntity>

    @Query("DELETE FROM results WHERE id = :id")
    suspend fun deleteById(id: Long)
}