package com.corsoft.data.api.database

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {
    suspend fun insert(item: T)
    suspend fun getById(id: String): T?
    fun observeAll(): Flow<List<T>>
}