package com.corsoft.services.internal.screen.service_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.list.ServiceList
import com.corsoft.services.internal.model.ServiceModel
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
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

    ServiceListScreen(
        state = uiState,
        navigator = navigator,
        onServiceClick = { navigator.navigate(it.destination) }
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
    onServiceClick: (ServiceModel) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.services)
                    )
                }
            )
        }
    ) { paddingValues ->
        ServiceList(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            navigator = navigator
        )
    }
}


@Preview
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