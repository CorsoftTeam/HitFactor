package com.corsoft.services.internal.screen.results

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity

@Immutable
internal data class ResultsScreenState(
    val results: List<ResultEntity> = emptyList()
) : MviState

internal sealed interface ResultsAction : MviAction {
    data class OnDelete(val id: Long) : ResultsAction
}

internal sealed interface ResultsEffect : MviEffect