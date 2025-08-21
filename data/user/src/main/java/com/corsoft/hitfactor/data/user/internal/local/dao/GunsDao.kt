package com.corsoft.hitfactor.data.user.internal.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.corsoft.data.api.database.BaseDao
import com.corsoft.hitfactor.data.user.api.entities.GunEntity

@Dao
interface GunsDao : BaseDao<GunEntity> {
    @Query("SELECT * FROM guns WHERE id = :id")
    override suspend fun getById(id: String): GunEntity?

    @Query("SELECT * FROM guns")
    override suspend fun observeAll(): List<GunEntity>

    @Query("DELETE FROM guns WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE guns SET shotCount = shotCount + :increment WHERE id = :gunId")
    suspend fun incrementShotCount(gunId: String, increment: Int = 1)

    @Query("UPDATE guns SET shotCount = 0 WHERE id = :gunId")
    suspend fun cleanGun(gunId: String)
}