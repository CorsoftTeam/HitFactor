package com.corsoft.services.internal.screen.ranges

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.services.internal.model.CityModel
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.model.RangeModel

@Immutable
internal data class RangesScreenState(
    val citiesList: List<CityModel> = emptyList(),
    val currentCity: CityModel = CityModel(),
    val rangeList: List<RangeModel> = emptyList()
) : MviState

internal sealed interface RangesAction : MviAction {
    data object Refresh: RangesAction
    data class ChangeCity(val city: String): RangesAction
}

internal sealed interface RangesEffect : MviEffect