package com.corsoft.services.internal.screen.add_weapon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.enum.GunTypeEnum
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
internal fun AddWeaponScreen(
    navigator: DestinationsNavigator,
    viewModel: AddWeaponViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is AddWeaponEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

            is AddWeaponEffect.Back -> {
                navigator.popBackStack()
            }
        }
    }

    AddWeaponScreen(
        state = uiState,
        onNameChange = { viewModel.onAction(AddWeaponAction.ChangeName(it)) },
        onSerialNumberChange = { viewModel.onAction(AddWeaponAction.ChangeSerialNumber(it)) },
        onCaliberChange = { viewModel.onAction(AddWeaponAction.ChangeCaliber(it)) },
        onTypeChange = { viewModel.onAction(AddWeaponAction.ChangeGunType(it)) },
        onAddClick = { viewModel.onAction(AddWeaponAction.AddWeapon) },
        snackbarHostState = snackBarHostState
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun AddWeaponScreen(
    modifier: Modifier = Modifier,
    state: AddWeaponScreenState,
    onNameChange: (String) -> Unit = {},
    onCaliberChange: (String) -> Unit = {},
    onTypeChange: (String) -> Unit = {},
    onSerialNumberChange: (String) -> Unit = {},
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
                        text = stringResource(id = CoreStringRes.weapon_adding)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.naming),
                text = state.name,
                onTextChange = onNameChange
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.serial_number),
                text = state.serialNumber,
                onTextChange = onSerialNumberChange
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.caliber),
                text = state.caliber,
                onTextChange = onCaliberChange
            )
            HFDropdownMenu(
                selectedOption = state.gunType.getName(),
                options = GunTypeEnum.entries.map { it.getName() },
                onValueChange = onTypeChange
            )
        }
    }
}


@Preview
@Composable
private fun AddWeaponPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            AddWeaponScreen(
                state = AddWeaponScreenState(
                    caliber = "7.62x39"
                )
            )
        }
    }
}