package com.corsoft.hitfactor.navigation.navigators

import com.corsoft.services.api.ServicesNavigator
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthGraph
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class ServicesNavigatorImpl(
    private val navigator: DestinationsNavigator
): ServicesNavigator {
    override fun logout() {
        navigator.navigate(AuthGraph) {
            launchSingleTop = true
            popUpTo(ServicesGraph) { inclusive = true }
        }
    }
}