package com.corsoft.hitfactor.app

import LoadingCircle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.corsoft.hitfactor.navigation.HFRootNavGraph
import com.corsoft.hitfactor.navigation.navigators.AuthNavigatorImpl
import com.corsoft.hitfactor.navigation.navigators.PaymentsNavigatorImpl
import com.corsoft.hitfactor.navigation.navigators.ServicesNavigatorImpl
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.bottombar.BottomNavigationBar
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthGraph
import com.ramcosta.composedestinations.generated.navgraphs.PaymentsGraph
import com.ramcosta.composedestinations.generated.services.destinations.DocumentsScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.ServiceListScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TimerScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.WeaponsScreenDestination
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.Route
import com.ramcosta.composedestinations.utils.currentDestinationFlow
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

enum class NavigationBarItem(
    @DrawableRes val icon: Int,
    @DrawableRes val iconOutline: Int,
    @StringRes val titleRes: Int
) {
    TIMER(
        CoreDrawableRes.ic_timer,
        CoreDrawableRes.ic_timer_outline,
        CoreStringRes.timer
    ),
    SERVICES(
        CoreDrawableRes.ic_services,
        CoreDrawableRes.ic_services_outline,
        CoreStringRes.services
    ),
    WEAPONS(
        CoreDrawableRes.ic_gun,
        CoreDrawableRes.ic_gun,
        CoreStringRes.gun_storage
    ),
}

@Composable
internal fun App(
    viewModel: AppViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val appState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val destinationNav = navController.rememberDestinationsNavigator()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is AppEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    navController.currentDestinationFlow.observeWithLifecycle { destination ->
        viewModel.onAction(AppAction.SetBottomBarVisibility(isBottomBarVisible(route = destination.route)))
    }

    HitFactorTheme {
        AppContainer(
            modifier = Modifier,
            items = appState.bottomBarItems,
            isLoading = appState.isLoading,
            selectedBottomBarItem = appState.selectedBottomBarItem,
            isBottomBarVisible = appState.isBottomBarVisibility,
            onBottomBarItemClick = {
                viewModel.onAction(AppAction.SetBottomBarItem(it))
                when (it) {
                    NavigationBarItem.SERVICES -> {
                        destinationNav.navigate(ServicesGraph)
                    }

                    NavigationBarItem.TIMER -> {
                        destinationNav.navigate(TimerScreenDestination)
                    }

                    NavigationBarItem.WEAPONS -> {
                        destinationNav.navigate(WeaponsScreenDestination)
                    }
                }
            }
        ) {
            DestinationsNavHost(
                navGraph = HFRootNavGraph,
                start = ServicesGraph,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(
                        AuthNavigatorImpl(destinationsNavigator)
                    )
                    dependency(
                        PaymentsNavigatorImpl(destinationsNavigator)
                    )
                    dependency(
                        ServicesNavigatorImpl(destinationsNavigator)
                    )
                }
            )
        }
    }
}

@Composable
private fun AppContainer(
    modifier: Modifier,
    items: List<NavigationBarItem>,
    isBottomBarVisible: Boolean,
    selectedBottomBarItem: NavigationBarItem,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    isLoading: Boolean,
    onBottomBarItemClick: (NavigationBarItem) -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(
                    items = remember {
                        items.map { context.getString(it.titleRes) }
                            .toList()
                    },
                    icons = remember {
                        items.map { it.icon }.toList()
                    },
                    iconsOutline = remember {
                        items.map { it.iconOutline }.toList()
                    },
                    selectedItem = items.indexOf(selectedBottomBarItem),
                    onItemClick = { onBottomBarItemClick(items[it]) },
                )
            }
        },
        topBar = {
            HFSnackBarHost(hostState = snackBarHostState)
        },
        modifier = modifier
    ) { contentPadding ->
        when {
            isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingCircle()
                }
            }

            else -> Box(
                modifier = Modifier.padding(contentPadding)
            ) {
                content()
            }
        }
    }
}

private fun isBottomBarVisible(route: String?): Boolean {
    return route != null && route in listOf(
        ServiceListScreenDestination.route,
        TimerScreenDestination.route,
        WeaponsScreenDestination.route
    )
}
