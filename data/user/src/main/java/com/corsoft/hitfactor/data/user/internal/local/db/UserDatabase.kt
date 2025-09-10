package com.corsoft.hitfactor.data.user.internal.local.db

import androidx.room.Database
import com.corsoft.data.api.database.BaseDao
import com.corsoft.data.api.database.BaseRoomDatabase
import com.corsoft.hitfactor.data.user.api.entities.ComplexEntity
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import com.corsoft.hitfactor.data.user.api.entities.GunEntity
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity
import com.corsoft.hitfactor.data.user.api.entities.TrainingEntity
import com.corsoft.hitfactor.data.user.internal.local.dao.ComplexesDao
import com.corsoft.hitfactor.data.user.internal.local.dao.GunDocumentsDao
import com.corsoft.hitfactor.data.user.internal.local.dao.GunsDao
import com.corsoft.hitfactor.data.user.internal.local.dao.ResultsDao
import com.corsoft.hitfactor.data.user.internal.local.dao.TrainingsDao

@Database(
    entities = [GunDocumentsEntity::class, GunEntity::class, TrainingEntity::class, ResultEntity::class, ComplexEntity::class],
    version = 2
)
abstract class UserDatabase : BaseRoomDatabase() {
    abstract fun gunDocumentsDao(): GunDocumentsDao
    abstract fun gunsDao(): GunsDao
    abstract fun trainingsDao(): TrainingsDao
    abstract fun resultsDao(): ResultsDao
    abstract fun complexesDao(): ComplexesDao

    override fun <T : BaseDao<E>, E> getDao(daoClass: Class<T>): T {
        return when (daoClass) {
            GunDocumentsDao::class.java -> gunDocumentsDao() as T
            GunsDao::class.java -> gunsDao() as T
            TrainingsDao::class.java -> trainingsDao() as T
            ResultsDao::class.java -> resultsDao() as T
            ComplexesDao::class.java -> complexesDao() as T
            else -> throw IllegalArgumentException("Unknown DAO type")
        }
    }
}