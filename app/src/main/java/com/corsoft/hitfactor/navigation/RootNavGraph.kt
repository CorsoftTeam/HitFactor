package com.corsoft.hitfactor.navigation

import com.ramcosta.composedestinations.animations.NavHostAnimatedDestinationStyle
import com.ramcosta.composedestinations.animations.defaults.NoTransitions
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthGraph
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.DirectionNavHostGraphSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.TypedRoute

object HFRootNavGraph : DirectionNavHostGraphSpec {

    override val defaultTransitions: NavHostAnimatedDestinationStyle = NoTransitions

    override val destinations: List<DestinationSpec> = listOf()

    override val nestedNavGraphs: List<NavGraphSpec> = listOf(
        AuthGraph,
        ServicesGraph
    )

    override val route: String = "root"

    override val startRoute: TypedRoute<Unit> = AuthGraph
}