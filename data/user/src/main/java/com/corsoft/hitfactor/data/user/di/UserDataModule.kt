package com.corsoft.hitfactor.data.user.di

import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.internal.UserRepositoryImpl
import com.corsoft.hitfactor.data.user.internal.network.UserApi
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val userDataModule = module {
    factory { get<Retrofit>().create<UserApi>() }
    single {
        UserRepositoryImpl(
            userApi = get()
        )
    } bind UserRepository::class
}