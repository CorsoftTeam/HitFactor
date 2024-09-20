package com.corsoft.services.internal.screen.service_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.model.ServiceModel
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>(start = true)
internal fun ServiceListScreen(
    navigator: DestinationsNavigator,
    viewModel: ServiceListViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HFSnackBarHost(
                hostState = snackBarHostState,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        ServiceListScreen(
            modifier = Modifier.padding(paddingValues),
            state = uiState,
            onServiceClick = { navigator.navigate(it.destination) }
        )
    }
}

@Composable
private fun ServiceListScreen(
    modifier: Modifier = Modifier,
    state: ServiceListScreenState,
    onServiceClick: (ServiceModel) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        //TODO: service list
    }
}