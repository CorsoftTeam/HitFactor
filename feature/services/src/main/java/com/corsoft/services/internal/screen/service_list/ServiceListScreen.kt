package com.corsoft.services.internal.screen.service_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.list.ServiceList
import com.corsoft.services.internal.component.list.ServiceList2
import com.corsoft.services.internal.model.ServiceModel
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>(start = true)
internal fun ServiceListScreen(
    navigator: DestinationsNavigator,
    viewModel: ServiceListViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(ServiceListAction.Refresh)
    }

    ServiceListScreen(
        state = uiState,
        navigator = navigator,
        onModeChange = { viewModel.onAction(ServiceListAction.ChangeMode) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun ServiceListScreen(
    modifier: Modifier = Modifier,
    state: ServiceListScreenState,
    navigator: DestinationsNavigator,
    onModeChange: () -> Unit = {},
    onServiceClick: (ServiceModel) -> Unit = {}
) {
    Scaffold(
        topBar = {
            Column {
                ToolBar(
                    title = {
                        ToolBar(
                            title = {
                                Text(
                                    text = stringResource(id = CoreStringRes.services)
                                )
                            },
                            actions = {
                                HFIconButton(
                                    icon =
                                        if (state.isServiceModeGrid) CoreDrawableRes.ic_services
                                        else CoreDrawableRes.ic_list
                                ) {
                                    onModeChange()
                                }
                                HFIconButton(icon = CoreDrawableRes.ic_settings) {
                                    navigator.navigate(SettingsScreenDestination)
                                }
                            }
                        )
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
//            InfoCard(
//                modifier = Modifier.padding(horizontal = 8.dp),
//                text = stringResource(CoreStringRes.you_can_change_service_list)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
            if (state.isServiceModeGrid) {
                ServiceList(
                    serviceList = state.serviceGroup.serviceList,
                    context = LocalContext.current,
                    navigator = navigator
                )
            } else {
                ServiceList2(
                    serviceList = state.serviceGroup.serviceList,
                    context = LocalContext.current,
                    navigator = navigator
                )
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
private fun ServicesPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            ServiceListScreen(
                state = ServiceListScreenState(),
                navigator = rememberNavController().rememberDestinationsNavigator()
            )
        }
    }
}