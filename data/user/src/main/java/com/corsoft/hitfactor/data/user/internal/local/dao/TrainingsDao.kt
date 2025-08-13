package com.corsoft.hitfactor.data.user.internal.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.corsoft.data.api.database.BaseDao
import com.corsoft.hitfactor.data.user.api.entities.TrainingEntity

@Dao
interface TrainingsDao : BaseDao<TrainingEntity> {
    @Query("SELECT * FROM trainings WHERE id = :id")
    override suspend fun getById(id: String): TrainingEntity?

    @Query("SELECT * FROM trainings")
    override suspend fun observeAll(): List<TrainingEntity>

    @Query("DELETE FROM trainings WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("""
        UPDATE trainings SET 
        dateTime = CASE WHEN :dateTime IS NULL THEN dateTime ELSE :dateTime END,
        length = CASE WHEN :length IS NULL THEN length ELSE :length END,
        hfScore = CASE WHEN :hfScore IS NULL THEN hfScore ELSE :hfScore END,
        note = CASE WHEN :note IS NULL THEN note ELSE :note END,
        weaponId = CASE WHEN :weaponId IS NULL THEN weaponId ELSE :weaponId END,
        shotCount = CASE WHEN :shotCount IS NULL THEN shotCount ELSE :shotCount END
        WHERE id = :id
    """)
    suspend fun updateTrainingFields(
        id: String,
        dateTime: String?,
        length: Int?,
        hfScore: Float?,
        note: String?,
        weaponId: String?,
        shotCount: Int?
    )
}