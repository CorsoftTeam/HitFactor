package com.corsoft.hitfactor.app

import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

data class AppModel(
    val selectedBottomBarItem: NavigationBarItem = NavigationBarItem.RECEIPTS,
    val bottomBarItems: List<NavigationBarItem> = NavigationBarItem.entries,
    val isAuth: Boolean? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isInternetAvailable: Boolean = true,
    val isBottomBarVisibility: Boolean = false
) : MviState

sealed interface AppAction : MviAction {
    data class SetBottomBarItem(val selectedItem: NavigationBarItem) : AppAction

    data class SetBottomBarVisibility(val isVisible: Boolean) : AppAction
}

sealed interface AppEffect : MviEffect {
    data class ShowError(val message: String) : AppEffect

}