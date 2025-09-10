package com.corsoft.services.internal.screen.add_complex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.enum.ClickPriceEnum
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.dropdown.HFDropdownMenu
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.text_field.HFFilledTextField
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun AddComplexScreen(
    navigator: DestinationsNavigator,
    viewModel: AddComplexViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is AddComplexEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

            is AddComplexEffect.Back -> {
                navigator.popBackStack()
            }
        }
    }

    AddComplexScreen(
        state = uiState,
        onNameChange = { viewModel.onAction(AddComplexAction.ChangeName(it)) },
        onZeroRangeChange = { viewModel.onAction(AddComplexAction.ChangeZeroRange(it)) },
        onCLickPriceChange = { viewModel.onAction(AddComplexAction.ChangeClickPrice(it)) },
        onSightHeightChange = { viewModel.onAction(AddComplexAction.ChangeSightHeight(it)) },
        onMuzzleVelocityChange = { viewModel.onAction(AddComplexAction.ChangeMuzzleVelocity(it)) },
        onBallisticCoefficientChange = {
            viewModel.onAction(
                AddComplexAction.ChangeBallisticCoefficient(
                    it
                )
            )
        },
        onAddClick = { viewModel.onAction(AddComplexAction.AddComplex) },
        snackbarHostState = snackBarHostState
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun AddComplexScreen(
    modifier: Modifier = Modifier,
    state: AddComplexScreenState,
    onNameChange: (String) -> Unit = {},
    onZeroRangeChange: (String) -> Unit = {},
    onSightHeightChange: (String) -> Unit = {},
    onCLickPriceChange: (String) -> Unit = {},
    onMuzzleVelocityChange: (String) -> Unit = {},
    onBallisticCoefficientChange: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    HFSnackBarHost(
        hostState = snackbarHostState,
        modifier = Modifier.statusBarsPadding()
    )
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.new_complex)
                    )
                }
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
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.naming),
                text = state.name,
                onTextChange = onNameChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(CoreStringRes.rifle)
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.zero_range_m),
                text = if (state.zeroRange == 0) "" else state.zeroRange.toString(),
                keyboardType = KeyboardType.Decimal,
                onTextChange = onZeroRangeChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(CoreStringRes.sight)
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.sight_height_cm),
                text = state.sightHeight,
                keyboardType = KeyboardType.Number,
                onTextChange = onSightHeightChange
            )
            HFDropdownMenu(
                selectedOption = stringResource(
                    CoreStringRes.click_in_moa_mil,
                    state.clickPrice.key
                ),
                options = ClickPriceEnum.entries.map { it.key },
                onValueChange = onCLickPriceChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(CoreStringRes.shell)
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.muzzle_velocity_ms),
                text = if (state.muzzleVelocity == 0) "" else state.muzzleVelocity.toString(),
                keyboardType = KeyboardType.Decimal,
                onTextChange = onMuzzleVelocityChange
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.ballistic_coefficient),
                text = state.ballisticCoefficient,
                keyboardType = KeyboardType.Number,
                onTextChange = onBallisticCoefficientChange
            )

        }
    }
}


@Preview(apiLevel = 34)
@Composable
private fun AddComplexPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            AddComplexScreen(
                state = AddComplexScreenState(
                )
            )
        }
    }
}