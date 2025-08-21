package com.corsoft.services.internal.screen.training_details

import LoadingCircle
import android.content.Intent
import android.provider.CalendarContract
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.MultiParameterCard
import com.corsoft.services.internal.component.card.ParameterColumnCard
import com.corsoft.services.internal.model.TrainingModel
import com.corsoft.services.internal.screen.complete_training.navigation.CompleteTrainingNavArgs
import com.corsoft.services.internal.screen.training_details.navigation.TrainingDetailsNavArgs
import com.corsoft.services.internal.screen.weapons.WeaponsAction
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.CompleteTrainingScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.WeaponDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId

@Composable
@Destination<ServicesNavGraph>(navArgs = TrainingDetailsNavArgs::class)
internal fun TrainingDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: TrainingDetailsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val trainingName = stringResource(CoreStringRes.training)

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            TrainingDetailsEffect.Back -> navigator.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.onAction(TrainingDetailsAction.Refresh)
    }

    TrainingDetailsScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        onDeleteClick = {
            viewModel.onAction(TrainingDetailsAction.Delete)
            navigator.popBackStack()
        },
        onCompleteClick = {
            navigator.navigate(
                CompleteTrainingScreenDestination(
                    CompleteTrainingNavArgs(uiState.trainingModel.id)
                )
            )
        },
        onCalendarAddClick = {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, trainingName)
                putExtra(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME, uiState.trainingModel.dateTime.atZone(
                        ZoneId.systemDefault()
                    )
                        .toInstant()
                        .toEpochMilli()
                )
            }
            context.startActivity(intent)
        },
        onCheckGunStateClick = { navigator.navigate(WeaponDetailsScreenDestination(it)) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun TrainingDetailsScreen(
    modifier: Modifier = Modifier,
    state: TrainingDetailsScreenState,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onCompleteClick: () -> Unit = {},
    onCalendarAddClick: () -> Unit = {},
    onCheckGunStateClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.training)
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
            if (state.trainingModel.shotCount == 0) {
                HFButton(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = CoreStringRes.end_training),
                    onClick = onCompleteClick
                )
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = modifier.fillMaxSize(),
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
                if (state.usedGun != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .shimmer(),
                        painter = painterResource(id = state.usedGun.gunType.getImageRes()),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (state.trainingModel.shotCount <= 0) {
                    MultiParameterCard(
                        params = mapOf(
                            Pair(
                                stringResource(id = CoreStringRes.date_and_time),
                                state.formattedDateTime
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.length_of_training),
                                state.trainingModel.length.toString()
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.weapon),
                                state.usedGun?.name ?: stringResource(id = CoreStringRes.club_gun)
                            ),
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HFButton(
                        isPrimary = false,
                        text = stringResource(CoreStringRes.add_to_calendar),
                        onClick = onCalendarAddClick
                    )
                    if (state.usedGun != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HFButton(
                            isPrimary = false,
                            text = stringResource(CoreStringRes.check_gun_state)
                        ) {
                            onCheckGunStateClick(state.usedGun.id)
                        }
                    }
                } else {
                    MultiParameterCard(
                        params = mapOf(
                            Pair(
                                stringResource(id = CoreStringRes.date_and_time),
                                state.formattedDateTime
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.length_of_training),
                                state.trainingModel.length.toString()
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.weapon),
                                state.usedGun?.name ?: stringResource(id = CoreStringRes.club_gun)
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.hit_factor),
                                state.trainingModel.hfScore.toString()
                            ),
                            Pair(
                                stringResource(id = CoreStringRes.shots),
                                state.trainingModel.shotCount.toString()
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ParameterColumnCard(
                        name = stringResource(id = CoreStringRes.note),
                        value = state.trainingModel.note
                    )
                }
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
                //TODO: idea: add useful tips
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
            TrainingDetailsScreen(
                state = TrainingDetailsScreenState(
                    isLoading = false,
                    trainingModel = TrainingModel()
                )
            )
        }
    }
}