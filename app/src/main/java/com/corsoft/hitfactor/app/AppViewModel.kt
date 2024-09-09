package com.corsoft.hitfactor.app

import com.corsoft.common.mvvm.MviViewModel

class AppViewModel : MviViewModel<AppModel, AppAction, AppEffect>(AppModel()) {

    override fun onAction(action: AppAction) {
        when (action) {
            is AppAction.SetBottomBarItem -> setBottomBarItem(action.selectedItem)
            is AppAction.SetBottomBarVisibility -> setBottomBarVisibility(action.isVisible)
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

    fun setLoading(loading: Boolean) {
        changeState { state ->
            state.copy(isLoading = loading)
        }
    }
}