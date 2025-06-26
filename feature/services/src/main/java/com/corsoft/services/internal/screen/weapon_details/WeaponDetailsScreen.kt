package com.corsoft.services.internal.screen.weapon_details

import LoadingCircle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.ParameterCard
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.screen.weapon_details.navigation.WeaponDetailsNavArgs
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.WarningCard
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.AddWeaponScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>(navArgs = WeaponDetailsNavArgs::class)
internal fun WeaponDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: WeaponDetailsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            WeaponDetailsEffect.Back -> navigator.popBackStack()
        }
    }

    WeaponDetailsScreen(
        state = uiState,
        onAddClick = { navigator.navigate(AddWeaponScreenDestination) },
        onBackClick = { navigator.popBackStack() },
        onDeleteClick = { viewModel.onAction(WeaponDetailsAction.Delete) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun WeaponDetailsScreen(
    modifier: Modifier = Modifier,
    state: WeaponDetailsScreenState,
    onAddClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = state.gunModel.name
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
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .shimmer(),
                    painter = painterResource(id = state.gunModel.gunType.getImageRes()),
                    contentDescription = ""
                )
                if (state.gunModel.shotCount > 100) {
                    Spacer(modifier = Modifier.height(16.dp))
                    WarningCard(
                        text = stringResource(
                            id = if (state.gunModel.shotCount > 200)
                                CoreStringRes.need_clean_immediately
                            else
                                CoreStringRes.need_clean
                        ),
                        isStrong = state.gunModel.shotCount > 200
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                ParameterCard(
                    name = stringResource(id = CoreStringRes.serial_number),
                    value = state.gunModel.serialNumber
                )
                Spacer(modifier = Modifier.height(16.dp))
                ParameterCard(
                    name = stringResource(id = CoreStringRes.weapon_type),
                    value = state.gunModel.gunType.getName()
                )
                Spacer(modifier = Modifier.height(16.dp))
                ParameterCard(
                    name = stringResource(id = CoreStringRes.caliber),
                    value = state.gunModel.caliber
                )
                Spacer(modifier = Modifier.height(16.dp))
                ParameterCard(
                    name = stringResource(id = CoreStringRes.shot_count),
                    value = state.gunModel.shotCount.toString()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { onDeleteClick() },
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = CoreStringRes.delete),
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.RedIcon
                    )
                }
            }
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
            WeaponDetailsScreen(
                state = WeaponDetailsScreenState(
                    gunModel = GunModel(
                        gunType = GunTypeEnum.BOLT_ACTION
                    )
                )
            )
        }
    }
}