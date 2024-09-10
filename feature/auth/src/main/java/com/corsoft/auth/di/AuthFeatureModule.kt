package com.corsoft.auth.di

import com.corsoft.auth.internal.screen.login.LoginViewModel
import com.corsoft.auth.internal.screen.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authFeatureModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}