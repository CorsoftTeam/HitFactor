package com.corsoft.services.internal.screen.trainings

import LoadingCircle
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.TrainingCard
import com.corsoft.services.internal.component.title.SimpleCalendarTitle
import com.corsoft.services.internal.screen.training_details.navigation.TrainingDetailsNavArgs
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.AppColors.Primary
import com.corsoft.ui.theme.HitFactorTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.AddTrainingScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TrainingDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val selectedItemColor: Color @Composable get() = Primary

@Composable
@Destination<ServicesNavGraph>
internal fun TrainingsScreen(
    navigator: DestinationsNavigator,
    viewModel: TrainingsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(TrainingsAction.Refresh)
    }

    TrainingsScreen(
        state = uiState,
        onNewTrainingClick = {
            navigator.navigate(AddTrainingScreenDestination)
        },
        onTrainingClick = {
            navigator.navigate(TrainingDetailsScreenDestination(TrainingDetailsNavArgs(it)))
        },
        onBackCLick = {
            navigator.popBackStack()
        }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun TrainingsScreen(
    modifier: Modifier = Modifier,
    state: TrainingsScreenState,
    onNewTrainingClick: () -> Unit = {},
    onTrainingClick: (String) -> Unit = {},
    onBackCLick: () -> Unit = {}
) {
    var selection by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.trainings)
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = onBackCLick
                    )
                },
            )
        },
        bottomBar = {
            HFButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = CoreStringRes.new_training),
                onClick = onNewTrainingClick
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
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
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                val coroutineScope = rememberCoroutineScope()
                SimpleCalendarTitle(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    currentMonth = calendarState.firstVisibleMonth.yearMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.previousMonth)
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.nextMonth)
                        }
                    },
                )
                HorizontalCalendar(
                    modifier = Modifier
                        .wrapContentWidth(),
                    state = calendarState,
                    dayContent = { day ->
                        Day(
                            day = day,
                            isSelected = selection == day.date,
                            trainingLength = state.trainings.firstOrNull {
                                it.dateTime.toLocalDate() == day.date
                            }?.length ?: 0
                        ) { clicked ->
                            selection = clicked.date
                        }
                    },
                    monthHeader = {
                        MonthHeader(
                            modifier = Modifier.padding(vertical = 8.dp),
                            daysOfWeek = daysOfWeek,
                        )
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                val trainings = state.trainings.filter { it.dateTime.toLocalDate() == selection }
                if (trainings.isEmpty()) {
                    Text(
                        text = stringResource(id = CoreStringRes.there_is_no_trainings),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    trainings.forEach {
                        TrainingCard(trainingModel = it) {
                            onTrainingClick(it.id)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    trainingLength: Int = 0,
    onClick: (CalendarDay) -> Unit = {},
) {
    val color: Color = if (trainingLength == 0) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = trainingLength * 0.2f)
    }
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) selectedItemColor else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(1.dp)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(4.dp)
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> MaterialTheme.colorScheme.onPrimary
            DayPosition.InDate, DayPosition.OutDate -> MaterialTheme.colorScheme.onSurfaceVariant
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp),
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                fontSize = 16.sp,
            )
// Lines
//            Column(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                verticalArrangement = Arrangement.spacedBy(6.dp),
//            ) {
//                for (color in colors) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(5.dp)
//                            .background(color),
//                    )
//                }
//            }
        }
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.White,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                fontWeight = FontWeight.Light,
            )
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
            TrainingsScreen(
                state = TrainingsScreenState(
                    isLoading = false
                )
            )
        }
    }
}