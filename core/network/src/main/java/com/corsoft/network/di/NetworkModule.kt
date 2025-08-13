package com.corsoft.network.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.corsoft.network.createOkHttpClient
import com.corsoft.network.createRetrofit
import com.corsoft.network.internal.interceptor.AuthInterceptor
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vaskorr.provider.CoreNetworkConfigProvider
import org.koin.dsl.module

val networkModule = module {
    single {
        createOkHttpClient(
            authInterceptor = AuthInterceptor(get()),
            chuckerInterceptor = ChuckerInterceptor(get()),
            isProdReleaseFlavor = get<CoreNetworkConfigProvider>().isProdReleaseFlavor()
        )
    }
    single {
        FirebaseAuth.getInstance()
    }
    single {
        FirebaseFirestore.getInstance()
    }
    single {
        Firebase.analytics
    }
    single { createRetrofit(get()) }
}