package com.corsoft.services.internal.screen.ballistics_calc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.hitfactor.data.user.api.entities.ComplexEntity
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.MultiParameterCard
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.dropdown.HFDropdownMenu
import com.corsoft.ui.components.placeholder.HFPlaceholder
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.AppColors.Primary
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.AddComplexScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.ZeroLineProperties
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun BallisticsCalcScreen(
    navigator: DestinationsNavigator,
    viewModel: BallisticsCalcViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(BallisticsCalcAction.Refresh)
    }

    BallisticsCalcScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        onRangeChange = { viewModel.onAction(BallisticsCalcAction.ChangeRange(it)) },
        onZeroRangeChange = { viewModel.onAction(BallisticsCalcAction.ChangeZeroRange(it)) },
        onCompleteRangeChange = { viewModel.onAction(BallisticsCalcAction.CompleteChangeRange) },
        onWindSpeedChange = { viewModel.onAction(BallisticsCalcAction.ChangeWindSpeed(it)) },
        onWindAngleChange = { viewModel.onAction(BallisticsCalcAction.ChangeWindAngle(it)) },
        onComplexAdd = { navigator.navigate(AddComplexScreenDestination) },
        onComplexSelect = { viewModel.onAction(BallisticsCalcAction.ChangeComplex(it)) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun BallisticsCalcScreen(
    modifier: Modifier = Modifier,
    state: BallisticsCalcScreenState,
    onBackClick: () -> Unit = {},
    onRangeChange: (Double) -> Unit = {},
    onCompleteRangeChange: () -> Unit = {},
    onZeroRangeChange: (Double) -> Unit = {},
    onWindSpeedChange: (Double) -> Unit = {},
    onWindAngleChange: (Double) -> Unit = {},
    onComplexAdd: () -> Unit = {},
    onComplexSelect: (String) -> Unit = {}
) {

    Scaffold(
        topBar = {
            Column {
                ToolBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = CoreStringRes.ballistics_calc)
                            )
                        }
                    },
                    navigationIcon = {
                        HFIconButton(
                            icon = CoreDrawableRes.ic_back,
                            onClick = onBackClick
                        )
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                HFDropdownMenu(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    selectedOption = state.selectedComplex?.name
                        ?: stringResource(CoreStringRes.select_weapon_complex),
                    options = state.complexes.map { it.name }
                ) {
                    onComplexSelect(it)
                }
                HFIconButton(
                    modifier = Modifier.weight(1f),
                    icon = CoreDrawableRes.ic_plus,
                    tint = Primary
                ) {
                    onComplexAdd()
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (state.selectedComplex != null) {
                Row {
                    Card(
                        modifier = modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(CoreDrawableRes.ic_vertical),
                                tint = Primary,
                                contentDescription = null
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.drop_cm),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = String.format("%.2f", state.verticalDrop),
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.clicks),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = if (state.verticalClicks > 0) "+" + state.verticalClicks.toString() else state.verticalClicks.toString(),
                                    fontSize = 32.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.moa),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = state.verticalMOAString,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.mil),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = state.verticalMILString,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Card(
                        modifier = modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.wind_drop_cm),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = String.format("%.2f", state.horizontalDrop),
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.clicks),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = if (state.horizontalClicks > 0) "+" + state.horizontalClicks.toString() else state.horizontalClicks.toString(),
                                    fontSize = 32.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.moa),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = state.horizontalMOAString,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = stringResource(CoreStringRes.mil),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                                    text = state.horizontalMILString,
                                    fontSize = 20.sp
                                )
                            }
                            Icon(
                                painter = painterResource(CoreDrawableRes.ic_horizontal),
                                tint = Primary,
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(CoreStringRes.distance_meters)
                            )
                            Text(
                                text = state.rangeM.toInt().toString()
                            )
                        }
                        Slider(
                            value = state.rangeM.toFloat(),
                            onValueChange = { newValue -> onRangeChange(newValue.toDouble()) },
                            valueRange = 0f..2000f,
                            steps = 39
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(CoreStringRes.zero_distance)
                            )
                            Text(
                                text = state.zeroRangeM.toInt().toString()
                            )
                        }
                        Slider(
                            value = state.zeroRangeM.toFloat(),
                            onValueChange = { newValue -> onZeroRangeChange(newValue.toDouble()) },
                            onValueChangeFinished = onCompleteRangeChange,
                            valueRange = 0f..2000f,
                            steps = 39
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(CoreStringRes.wind_speed_ms_d)
                            )
                            Text(
                                text = state.windSpeedMs.toInt().toString()
                            )
                        }
                        Slider(
                            value = state.windSpeedMs.toFloat(),
                            onValueChange = { newValue -> onWindSpeedChange(newValue.toDouble()) },
                            valueRange = 0f..20f,
                            steps = 19
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(CoreStringRes.wind_angle_d)
                            )
                            Text(
                                text = state.windAngleDeg.toInt().toString()
                            )
                        }
                        Slider(
                            value = state.windAngleDeg.toFloat(),
                            onValueChange = { newValue -> onWindAngleChange(newValue.toDouble()) },
                            valueRange = 0f..360f,
                            steps = 23
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                    text = stringResource(CoreStringRes.charts),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val chartName = stringResource(CoreStringRes.bullet_drop_cm_m)
                        LineChart(
                            gridProperties = GridProperties(
                                yAxisProperties = GridProperties.AxisProperties(
                                    lineCount = 11
                                )
                            ),
                            zeroLineProperties = ZeroLineProperties(
                                enabled = true,
                                color = SolidColor(Primary),
                            ),
                            curvedEdges = false,
                            labelHelperProperties = LabelHelperProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            labelProperties = LabelProperties(
                                enabled = true,
                                labels = (0..1000 step 100).map { it.toString() },
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            data = listOf(
                                Line(
                                    label = chartName,
                                    values = state.dropChartData,
                                    color = SolidColor(Primary)
                                )
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val chartName = stringResource(CoreStringRes.speed_ms_m)
                        LineChart(
                            gridProperties = GridProperties(
                                yAxisProperties = GridProperties.AxisProperties(
                                    lineCount = 11
                                )
                            ),
                            curvedEdges = false,
                            labelHelperProperties = LabelHelperProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            labelProperties = LabelProperties(
                                enabled = true,
                                labels = (0..1000 step 100).map { it.toString() },
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            data = listOf(
                                Line(
                                    label = chartName,
                                    values = state.speedChartData,
                                    color = SolidColor(Primary)
                                )
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val chartName = stringResource(CoreStringRes.time_s_m)
                        LineChart(
                            gridProperties = GridProperties(
                                yAxisProperties = GridProperties.AxisProperties(
                                    lineCount = 11
                                )
                            ),
                            curvedEdges = false,
                            labelHelperProperties = LabelHelperProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            labelProperties = LabelProperties(
                                enabled = true,
                                labels = (0..1000 step 100).map { it.toString() },
                                textStyle = TextStyle.Default.copy(
                                    color = White
                                )
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            data = listOf(
                                Line(
                                    label = chartName,
                                    values = state.timeChartData,
                                    color = SolidColor(Primary)
                                )
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                    text = stringResource(CoreStringRes.data),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                MultiParameterCard(
                    params = mapOf(
                        Pair(
                            stringResource(CoreStringRes.zero_range_m),
                            String.format("%.2f", state.zeroRangeM)
                        ),
                        Pair(
                            stringResource(CoreStringRes.sight_height_cm),
                            String.format("%.2f", state.selectedComplex.sightHeight * 100)
                        ),
                        Pair(
                            stringResource(CoreStringRes.click_price),
                            state.selectedComplex.clickPrice
                        ),
                        Pair(
                            stringResource(CoreStringRes.muzzle_velocity_ms),
                            state.selectedComplex.muzzleVelocity.toString()
                        ),
                        Pair(
                            stringResource(CoreStringRes.g1_coeff),
                            String.format("%.2f", state.selectedComplex.ballisticCoefficient)
                        ),
                        Pair(
                            stringResource(CoreStringRes.wind_speed_ms),
                            String.format("%.2f", state.windSpeedMs)
                        ),
                        Pair(
                            stringResource(CoreStringRes.wind_angle),
                            String.format("%.2f", state.windAngleDeg)
                        ),
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                HFPlaceholder(
                    text = stringResource(CoreStringRes.select_or_add_complex)
                )
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
private fun PreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            BallisticsCalcScreen(
                state = BallisticsCalcScreenState(
                    selectedComplex = ComplexEntity(
                        id = ""
                    )
                )
            )
        }
    }
}