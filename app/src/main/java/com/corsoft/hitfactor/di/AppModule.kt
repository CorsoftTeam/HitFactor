package com.corsoft.hitfactor.di

import com.corsoft.hitfactor.app.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AppViewModel)
}