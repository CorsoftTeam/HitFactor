package com.corsoft.services.internal.screen.add_training

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.ParameterClickableCard
import com.corsoft.services.internal.component.picker.HFDateTimePicker
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
@Destination<ServicesNavGraph>
internal fun AddTrainingScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTrainingViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is AddTrainingEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

            is AddTrainingEffect.Back -> {
                navigator.popBackStack()
            }
        }
    }

    AddTrainingScreen(
        state = uiState,
        onDateTimeChange = { viewModel.onAction(AddTrainingAction.ChangeDateTime(it)) },
        onLengthChange = { viewModel.onAction(AddTrainingAction.ChangeLength(it)) },
        onWeaponIdChange = { viewModel.onAction(AddTrainingAction.ChangeWeaponId(it)) },
        onAddClick = { viewModel.onAction(AddTrainingAction.AddTraining) },
        showDialog = { viewModel.onAction(AddTrainingAction.ShowDialog) },
        snackbarHostState = snackBarHostState
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )

    if (uiState.isDialogVisible) {
        HFDateTimePicker(
            onDateSelect = {
                viewModel.onAction(AddTrainingAction.ChangeDateTime(it))
                viewModel.onAction(AddTrainingAction.HideDialog)
            },
            onDismiss = {
                viewModel.onAction(AddTrainingAction.HideDialog)
            }
        )
    }
}

@Composable
private fun AddTrainingScreen(
    modifier: Modifier = Modifier,
    state: AddTrainingScreenState,
    onDateTimeChange: (LocalDateTime) -> Unit = {},
    onLengthChange: (Int) -> Unit = {},
    onWeaponIdChange: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
    showDialog: () -> Unit = {},
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
                        text = stringResource(id = CoreStringRes.new_training)
                    )
                }
            )
        },
        bottomBar = {
            HFButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = CoreStringRes.add),
                enabled = state.dateTime != null && state.length != null,
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
            ParameterClickableCard(
                name = stringResource(id = CoreStringRes.date_and_time),
                value = state.dateTime?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                    ?: "",
                onClick = showDialog
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.length_of_training),
                text = state.length?.toString() ?: "",
                onTextChange = { onLengthChange(it.toIntOrNull() ?: 0) },
                keyboardType = KeyboardType.Decimal
            )
            if (state.userGuns.isNotEmpty()) {
                HFDropdownMenu(
                    selectedOption = state.userGuns.firstOrNull { it.id == state.weaponId }?.name
                        ?: stringResource(
                            id = CoreStringRes.select_gun
                        ), //TODO add cancel weapon id
                    options = state.userGuns.map { it.name }, //TODO: fix repeat names
                    onValueChange = onWeaponIdChange
                )
            }
        }
    }
}


@Preview
@Composable
private fun AddTrainingPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            AddTrainingScreen(
                state = AddTrainingScreenState(
                )
            )
        }
    }
}