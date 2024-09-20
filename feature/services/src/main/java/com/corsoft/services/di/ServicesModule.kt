package com.corsoft.services.di

import com.corsoft.services.internal.screen.service_list.ServiceListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val servicesModule = module {
    viewModelOf(::ServiceListViewModel)
}