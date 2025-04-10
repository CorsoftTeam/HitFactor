package com.corsoft.services.di

import com.corsoft.services.internal.screen.documents.DocumentsViewModel
import com.corsoft.services.internal.screen.profile.ProfileViewModel
import com.corsoft.services.internal.screen.service_list.ServiceListViewModel
import com.corsoft.services.internal.screen.timer.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val servicesFeatureModule = module {
    viewModelOf(::ServiceListViewModel)
    viewModelOf(::TimerViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::DocumentsViewModel)
}