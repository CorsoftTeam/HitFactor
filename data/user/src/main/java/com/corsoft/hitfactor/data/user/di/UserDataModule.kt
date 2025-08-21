package com.corsoft.hitfactor.data.user.di

import com.corsoft.data.api.database.BaseRoomDatabase
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.internal.UserLocalRepositoryImpl
import com.corsoft.hitfactor.data.user.internal.local.db.UserDatabase
import com.corsoft.hitfactor.data.user.internal.network.UserApi
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val userDataModule = module {
    factory { get<Retrofit>().create<UserApi>() }
    single {
        UserLocalRepositoryImpl(
            auth = get(),
            firestore = get(),
            userDatabase = get(),
            localStorage = get()
        )
    } bind UserRepository::class

    single<UserDatabase> {
        BaseRoomDatabase.create(get(), "user_db")
    }
}