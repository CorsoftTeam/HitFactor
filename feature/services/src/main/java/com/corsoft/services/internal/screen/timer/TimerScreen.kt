package com.corsoft.services.internal.screen.timer

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.button.TimerButton
import com.corsoft.services.internal.component.enum.TimerStateEnum
import com.corsoft.services.internal.component.item.ShotTimeItem
import com.corsoft.services.internal.model.timer.ShotModel
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.CalculateHFScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Destination<ServicesNavGraph>
@Composable
internal fun TimerScreen(
    navigator: DestinationsNavigator,
    viewModel: TimerViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val hasAudioPermission = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    LaunchedEffect(hasAudioPermission) {
        if (!hasAudioPermission.status.isGranted) {
            hasAudioPermission.launchPermissionRequest()
        }
    }

    if (hasAudioPermission.status.isGranted) {
        viewModel.onAction(TimerAction.StartRecording)
    }

    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.timer)
                    )
                },
                actions = {
                    HFIconButton(icon = CoreDrawableRes.ic_settings) { }
                }
            )
            HFSnackBarHost(
                hostState = snackBarHostState,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        TimerScreen(
            modifier = Modifier.padding(paddingValues),
            state = uiState,
            actions = { action ->
                viewModel.onAction(action)
            },
            onCalculateButtonClick = { navigator.navigate(CalculateHFScreenDestination) }
        )
    }
}

@Composable
private fun TimerScreen(
    modifier: Modifier = Modifier,
    state: TimerState,
    actions: (TimerAction) -> Unit = {},
    onStartButtonClick: () -> Unit = {},
    onCalculateButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
        ) {
            Column(
                Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(state.shotTimes) { time ->
                        ShotTimeItem(
                            modifier = Modifier.animateItem(),
                            index = state.shotTimes.indexOf(time) + 1,
                            time = state.formatTime(time.time),
                            split = state.formatTime(time.split),
                            onDelete = { actions(TimerAction.DeleteTime(state.shotTimes.indexOf(time))) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        Column(
            Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.formatTime(state.time),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            if (state.timerState == TimerStateEnum.RUN) {
                Text(
                    text = state.formatTime(state.realTime),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        TimerButton(
            timerState = state.timerState,
            onStartButtonClick = {
                when (state.timerState) {
                    TimerStateEnum.STOPPED -> actions(TimerAction.StartCountDown)
                    TimerStateEnum.WAITING -> {}
                    TimerStateEnum.RUN -> actions(TimerAction.StopTimer)
                }
            },
            onCountHitFactorClick = {},
            readyTimerValue = state.readyTimerValue
        )
        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = state.timerState == TimerStateEnum.STOPPED
        ) {
            HFButton(
                modifier = Modifier.height(64.dp),
                text = stringResource(id = CoreStringRes.results),
                isPrimary = false,
                onClick = onCalculateButtonClick
            )
        }
    }
}

//@Preview
//@Composable
//private fun TimerPreview() {
//    HitFactorTheme {
//        Surface {
//            TimerScreen(
//                state = TimerState(
//                    shotTimes = listOf(
//                        ShotModel(15000, 15000),
//                        ShotModel(25000, 10000),
//                        ShotModel(35000, 10000),
//                    ),
//                    time = 36786,
//                )
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun TimerPreviewDark() {
//    HitFactorTheme(
//        darkTheme = true
//    ) {
//        Surface {
//            TimerScreen(
//                state = TimerState(
//                    shotTimes = listOf(
//                        ShotModel(15000, 15000),
//                        ShotModel(25000, 10000),
//                        ShotModel(35000, 10000),
//                    ),
//                    time = 36786,
//                    readyTimerValue = 4,
//                    timerState = TimerStateEnum.WAITING
//                )
//            )
//        }
//    }
//}

@Preview
@Composable
private fun TimerPreviewDarkStart() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TimerScreen(
                state = TimerState(
                    readyTimerValue = 4,
                    timerState = TimerStateEnum.STOPPED
                )
            )
        }
    }
}

@Preview
@Composable
private fun TimerPreviewDarkWaiting() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TimerScreen(
                state = TimerState(
                    readyTimerValue = 4,
                    timerState = TimerStateEnum.WAITING,
                )
            )
        }
    }
}

@Preview
@Composable
private fun TimerPreviewDarkRun() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TimerScreen(
                state = TimerState(
                    shotTimes = listOf(
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                    ),
                    time = 3453,
                    realTime = 4827,
                    timerState = TimerStateEnum.RUN,
                )
            )
        }
    }
}

@Preview
@Composable
private fun TimerPreviewDarkStop() {
    HitFactorTheme(
        darkTheme = false
    ) {
        Surface {
            TimerScreen(
                state = TimerState(
                    shotTimes = listOf(
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                        ShotModel(1156, 1156),
                        ShotModel(2568, 1412),
                        ShotModel(3453, 885),
                    ),
                    time = 3453,
                    timerState = TimerStateEnum.STOPPED,
                )
            )
        }
    }
}