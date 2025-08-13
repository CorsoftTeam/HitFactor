package com.corsoft.hitfactor.data.user.internal.local.db

import androidx.room.Database
import com.corsoft.data.api.database.BaseDao
import com.corsoft.data.api.database.BaseRoomDatabase
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import com.corsoft.hitfactor.data.user.api.entities.GunEntity
import com.corsoft.hitfactor.data.user.api.entities.TrainingEntity
import com.corsoft.hitfactor.data.user.internal.local.dao.GunDocumentsDao
import com.corsoft.hitfactor.data.user.internal.local.dao.GunsDao
import com.corsoft.hitfactor.data.user.internal.local.dao.TrainingsDao

@Database(
    entities = [GunDocumentsEntity::class, GunEntity::class, TrainingEntity::class],
    version = 1
)
abstract class UserDatabase : BaseRoomDatabase() {
    abstract fun gunDocumentsDao(): GunDocumentsDao
    abstract fun gunsDao(): GunsDao
    abstract fun trainingsDao(): TrainingsDao

    override fun <T : BaseDao<E>, E> getDao(daoClass: Class<T>): T {
        return when (daoClass) {
            GunDocumentsDao::class.java -> gunDocumentsDao() as T
            GunsDao::class.java -> gunsDao() as T
            TrainingsDao::class.java -> trainingsDao() as T
            else -> throw IllegalArgumentException("Unknown DAO type")
        }
    }
}