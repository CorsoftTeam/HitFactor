package com.corsoft.hitfactor.app

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.corsoft.auth.api.AuthRepository
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.hitfactor.navigation.HFRootNavGraph
import com.corsoft.hitfactor.navigation.navigators.AuthNavigatorImpl
import com.corsoft.hitfactor.navigation.navigators.PaymentsNavigatorImpl
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.bottombar.BottomNavigationBar
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthGraph
import com.ramcosta.composedestinations.generated.navgraphs.PaymentsGraph
import com.ramcosta.composedestinations.generated.services.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.ServiceListScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TimerScreenDestination
import com.ramcosta.composedestinations.generated.services.navgraphs.ServicesGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.Route
import com.ramcosta.composedestinations.utils.currentDestinationFlow
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

enum class NavigationBarItem(
    @DrawableRes val icon: Int,
    @DrawableRes val iconOutline: Int,
    @StringRes val titleRes: Int
) {
    SERVICES(
        CoreDrawableRes.ic_services,
        CoreDrawableRes.ic_services_outline,
        CoreStringRes.services
    ),
    TIMER(
        CoreDrawableRes.ic_timer,
        CoreDrawableRes.ic_timer_outline,
        CoreStringRes.timer
    ),
    PROFILE(
        CoreDrawableRes.ic_profile,
        CoreDrawableRes.ic_profile_outline,
        CoreStringRes.profile
    ),
}

@Composable
internal fun App(
    viewModel: AppViewModel = koinViewModel(),
    authRepository: AuthRepository = koinInject(),
    paymentsRepository: PaymentsRepository = koinInject()
) {

    val context = LocalContext.current
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
            isError = appState.isError,
            isInternetAvailable = appState.isInternetAvailable,
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
                    NavigationBarItem.PROFILE -> {
                        destinationNav.navigate(ProfileScreenDestination)
                    }
                }
            }
        ) {
            DestinationsNavHost(
                navGraph = HFRootNavGraph,
                startRoute = getNavGraph(authRepository, paymentsRepository),
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(
                        AuthNavigatorImpl(destinationsNavigator)
                    )
                    dependency(
                        PaymentsNavigatorImpl(destinationsNavigator)
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
    isInternetAvailable: Boolean,
    isError: Boolean,
    onBottomBarItemClick: (NavigationBarItem) -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
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
//            isLoading -> LoadingScreen()
//            !isInternetAvailable -> ErrorScreen(
//                error = stringResource(id = CoreStringRes.no_connection_internet)
//            )
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
        ProfileScreenDestination.route
    )
}

private fun getNavGraph(
    authRepository: AuthRepository,
    paymentsRepository: PaymentsRepository
): Route {
    return if (authRepository.isUserAuthorised()) {
        if (paymentsRepository.isSub()) {
            ServicesGraph
        } else {
            PaymentsGraph
        }
    } else {
        AuthGraph
    }
}
