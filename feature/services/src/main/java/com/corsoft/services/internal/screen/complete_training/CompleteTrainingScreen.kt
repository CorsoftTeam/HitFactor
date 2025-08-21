package com.corsoft.services.internal.screen.complete_training

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
import com.corsoft.services.internal.screen.complete_training.navigation.CompleteTrainingNavArgs
import com.corsoft.ui.components.button.HFButton
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
@Destination<ServicesNavGraph>(navArgs = CompleteTrainingNavArgs::class)
internal fun CompleteTrainingScreen(
    navigator: DestinationsNavigator,
    viewModel: CompleteTrainingViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is CompleteTrainingEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

            is CompleteTrainingEffect.Back -> {
                navigator.popBackStack()
            }
        }
    }

    CompleteTrainingScreen(
        state = uiState,
        onShotCountChange = { viewModel.onAction(CompleteTrainingAction.ChangeShotCount(it)) },
        onHfScoreChange = { viewModel.onAction(CompleteTrainingAction.ChangeHfScore(it)) },
        onNoteChange = { viewModel.onAction(CompleteTrainingAction.ChangeNote(it)) },
        onConfirmClick = { viewModel.onAction(CompleteTrainingAction.CompleteTraining) },
        snackbarHostState = snackBarHostState
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun CompleteTrainingScreen(
    modifier: Modifier = Modifier,
    state: CompleteTrainingScreenState,
    onHfScoreChange: (String) -> Unit = {},
    onShotCountChange: (Int) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    onConfirmClick: () -> Unit = {},
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
                        text = stringResource(id = CoreStringRes.end_training)
                    )
                }
            )
        },
        bottomBar = {
            HFButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = CoreStringRes.end_training),
                enabled = state.shotCount != 0,
                onClick = onConfirmClick
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
                placeholder = stringResource(id = CoreStringRes.shots),
                text = if (state.shotCount == 0) "" else state.shotCount.toString(),
                onTextChange = { onShotCountChange(it.toIntOrNull() ?: 0) },
                keyboardType = KeyboardType.Decimal
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.hit_factor),
                text = state.hfScore,
                onTextChange = onHfScoreChange,
                keyboardType = KeyboardType.Number
            )
            HFFilledTextField(
                placeholder = stringResource(id = CoreStringRes.note),
                singleLine = false,
                text = state.note,
                onTextChange = onNoteChange
            )
        }
    }
}


@Preview
@Composable
private fun PreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            CompleteTrainingScreen(
                state = CompleteTrainingScreenState(
                )
            )
        }
    }
}