package com.corsoft.services.di

import com.corsoft.services.internal.screen.add_complex.AddComplexViewModel
import com.corsoft.services.internal.screen.add_training.AddTrainingViewModel
import com.corsoft.services.internal.screen.add_weapon.AddWeaponViewModel
import com.corsoft.services.internal.screen.ballistics_calc.BallisticsCalcViewModel
import com.corsoft.services.internal.screen.calculate_hf.CalculateHFViewModel
import com.corsoft.services.internal.screen.complete_training.CompleteTrainingViewModel
import com.corsoft.services.internal.screen.documents.DocumentsViewModel
import com.corsoft.services.internal.screen.profile.ProfileViewModel
import com.corsoft.services.internal.screen.ranges.RangesViewModel
import com.corsoft.services.internal.screen.results.ResultsViewModel
import com.corsoft.services.internal.screen.service_list.ServiceListViewModel
import com.corsoft.services.internal.screen.settings.SettingsViewModel
import com.corsoft.services.internal.screen.timer.TimerViewModel
import com.corsoft.services.internal.screen.trainers.TrainersViewModel
import com.corsoft.services.internal.screen.training_details.TrainingDetailsViewModel
import com.corsoft.services.internal.screen.trainings.TrainingsViewModel
import com.corsoft.services.internal.screen.weapon_details.WeaponDetailsViewModel
import com.corsoft.services.internal.screen.weapon_docs.WeaponDocsViewModel
import com.corsoft.services.internal.screen.weapons.WeaponsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val servicesFeatureModule = module {
    viewModelOf(::ServiceListViewModel)
    viewModelOf(::TimerViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::DocumentsViewModel)
    viewModelOf(::CalculateHFViewModel)
    viewModelOf(::WeaponsViewModel)
    viewModelOf(::AddWeaponViewModel)
    viewModelOf(::WeaponDetailsViewModel)
    viewModelOf(::RangesViewModel)
    viewModelOf(::TrainingsViewModel)
    viewModelOf(::TrainersViewModel)
    viewModelOf(::AddTrainingViewModel)
    viewModelOf(::TrainingDetailsViewModel)
    viewModelOf(::CompleteTrainingViewModel)
    viewModelOf(::WeaponDocsViewModel)
    viewModelOf(::ResultsViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::BallisticsCalcViewModel)
    viewModelOf(::AddComplexViewModel)
}