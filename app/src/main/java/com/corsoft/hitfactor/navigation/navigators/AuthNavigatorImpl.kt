package com.corsoft.hitfactor.navigation.navigators

import com.corsoft.auth.api.AuthNavigator
import com.ramcosta.composedestinations.generated.navgraphs.PaymentsGraph
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.system.exitProcess

class AuthNavigatorImpl(private val navigator: DestinationsNavigator) : AuthNavigator {
    override fun back() {
        exitProcess(0)
    }

    override fun goToServices() {
        navigator.navigate(ServicesGraph)
    }

    override fun goToPayment() {
        navigator.navigate(PaymentsGraph)
    }
}