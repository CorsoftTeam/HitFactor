package com.corsoft.hitfactor.app

import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import kotlinx.coroutines.launch
import kotlin.math.truncate

class AppViewModel : MviViewModel<AppModel, AppAction, AppEffect>(AppModel()) {

    override fun onAction(action: AppAction) {
        when (action) {
            is AppAction.SetBottomBarItem -> setBottomBarItem(action.selectedItem)
            is AppAction.SetBottomBarVisibility -> setBottomBarVisibility(action.isVisible)
        }
    }

    init {
        changeState {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun setBottomBarVisibility(visible: Boolean) {
        changeState { state ->
            state.copy(isBottomBarVisibility = visible)
        }
    }

    private fun setBottomBarItem(selectedItem: NavigationBarItem) {
        changeState { state ->
            state.copy(selectedBottomBarItem = selectedItem)
        }
    }
}