package com.corsoft.data.api.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class BaseRoomDatabase : RoomDatabase() {
    abstract fun <T : BaseDao<E>, E> getDao(daoClass: Class<T>): T

    companion object {
        inline fun <reified DB : BaseRoomDatabase> create(
            context: Context,
            name: String
        ): DB {
            return Room.databaseBuilder(
                context.applicationContext,
                DB::class.java,
                name
            ).fallbackToDestructiveMigration(false)
                .build()
        }
    }
}