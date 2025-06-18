package com.corsoft.hitfactor.app

import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import kotlinx.coroutines.launch

class AppViewModel(
    private val paymentsRepository: PaymentsRepository
) : MviViewModel<AppModel, AppAction, AppEffect>(AppModel()) {

    override fun onAction(action: AppAction) {
        when (action) {
            is AppAction.SetBottomBarItem -> setBottomBarItem(action.selectedItem)
            is AppAction.SetBottomBarVisibility -> setBottomBarVisibility(action.isVisible)
        }
    }

    init {
        viewModelScope.launch {
            paymentsRepository.isSub { result ->
                changeState {
                    it.copy(
                        isSubscribed = result ?: false, //TODO: add error message
                        isLoading = false
                    )
                }
            }
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