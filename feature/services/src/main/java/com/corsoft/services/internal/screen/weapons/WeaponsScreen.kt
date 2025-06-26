package com.corsoft.services.internal.screen.weapons

import LoadingCircle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.list.GunList
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.screen.weapon_details.navigation.WeaponDetailsNavArgs
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.AddWeaponScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.WeaponDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun WeaponsScreen(
    navigator: DestinationsNavigator,
    viewModel: WeaponsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onAction(WeaponsAction.Refresh)
    }

    WeaponsScreen(
        state = uiState,
        onAddClick = { navigator.navigate(AddWeaponScreenDestination) },
        onItemClick = {
            navigator.navigate(
                WeaponDetailsScreenDestination(
                    WeaponDetailsNavArgs(it.id)
                )
            )
        },
        onBackClick = { navigator.popBackStack() }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun WeaponsScreen(
    modifier: Modifier = Modifier,
    state: WeaponsScreenState,
    onAddClick: () -> Unit = {},
    onItemClick: (GunModel) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.gun_storage)
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = onBackClick
                    )
                },
            )
        },
        bottomBar = {
            HFButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = CoreStringRes.add),
                onClick = onAddClick
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingCircle()
            }
        } else {
            GunList(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp),
                gunList = state.weaponsList,
                onItemClick = onItemClick
            )
        }
    }
}


@Preview
@Composable
private fun ServicesPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            WeaponsScreen(
                state = WeaponsScreenState()
            )
        }
    }
}