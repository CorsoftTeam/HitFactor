package com.corsoft.services.internal.screen.trainers

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.CityModel
import com.corsoft.services.internal.model.TrainerModel

@Immutable
internal data class TrainersScreenState(
    val citiesList: List<CityModel> = emptyList(),
    val currentCity: CityModel = CityModel(name = "Выберите город"),
    val trainersList: List<TrainerModel> = emptyList()
) : MviState

internal sealed interface TrainersAction : MviAction {
    data object Refresh: TrainersAction
    data class ChangeCity(val city: String): TrainersAction
}

internal sealed interface TrainersEffect : MviEffect