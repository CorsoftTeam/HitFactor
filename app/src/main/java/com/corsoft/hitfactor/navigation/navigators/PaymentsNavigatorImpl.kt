package com.corsoft.hitfactor.navigation.navigators

import com.corsoft.hitfactor.feature.payments.api.PaymentsNavigator
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class PaymentsNavigatorImpl(private val navigator: DestinationsNavigator) : PaymentsNavigator {
    override fun back() {
        navigator.navigate(ServicesGraph)
    }
}