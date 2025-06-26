package com.corsoft.services.internal.screen.trainings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

@RequiresApi(Build.VERSION_CODES.O)
internal class TrainingsViewModel(
    userRepository: UserRepository
): MviViewModel<TrainingsScreenState, TrainingsAction, TrainingsEffect>(
    TrainingsScreenState()
) {
    override fun onAction(action: TrainingsAction) {

    }

    init {
        viewModelScope.launch {
            setLoading(true)
            userRepository.getTrainings().doOn(
                success = {
                    changeState { state ->
                        state.copy(trainings = it.map { it.toUiModel() })
                    }
                },
                failed = {
                    //TODO: handle errors
                }
            )
            setLoading(false)
        }
    }

    private fun setLoading(state: Boolean){
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}