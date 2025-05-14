package com.corsoft.services.internal.screen.calculate_hf

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.common.formatTime
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.screen.calculate_hf.navigation.CalculateHFNavArgs
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
@Destination<ServicesNavGraph>(navArgs = CalculateHFNavArgs::class)
internal fun CalculateHFScreen(
    navigator: DestinationsNavigator,
    viewModel: CalculateHFViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalculateHFScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        addAlpha = { viewModel.onAction(CalculateHFAction.AddAlpha) },
        addCharlie = { viewModel.onAction(CalculateHFAction.AddCharlie) },
        addDelta = { viewModel.onAction(CalculateHFAction.AddDelta) },
        addMiss = { viewModel.onAction(CalculateHFAction.AddMiss) },
        addNoShoot = { viewModel.onAction(CalculateHFAction.AddNoShoot) },
        addProcedure = { viewModel.onAction(CalculateHFAction.AddProcedure) },
        reset = { viewModel.onAction(CalculateHFAction.Reset) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculateHFScreen(
    modifier: Modifier = Modifier,
    state: CalculateHFScreenState,
    addAlpha: () -> Unit = {},
    addCharlie: () -> Unit = {},
    addDelta: () -> Unit = {},
    addMiss: () -> Unit = {},
    addNoShoot: () -> Unit = {},
    addProcedure: () -> Unit = {},
    reset: () -> Unit = {},
    save: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.calculator)
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = onBackClick
                    )
                },
                actions = {
                    HFIconButton(icon = CoreDrawableRes.ic_settings) { }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = CoreStringRes.hit_factor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = String.format(Locale.US, "%.2f", state.hitFactor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 78.sp,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //TODO
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formatTime(state.time),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = CoreDrawableRes.ic_edit),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            id = CoreStringRes.x_alpha,
                            formatArgs = arrayOf(state.alphaCount)
                        )
                    )
                    Text(
                        text = stringResource(
                            id = CoreStringRes.x_charlie,
                            formatArgs = arrayOf(state.charlieCount)
                        )
                    )
                    Text(
                        text = stringResource(
                            id = CoreStringRes.x_delta,
                            formatArgs = arrayOf(state.deltaCount)
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(
                            id = CoreStringRes.miss_x,
                            formatArgs = arrayOf(state.missCount)
                        )
                    )
                    Text(
                        text = stringResource(
                            id = CoreStringRes.no_shoot_x,
                            formatArgs = arrayOf(state.noShootCount)
                        )
                    )
                    Text(
                        text = stringResource(
                            id = CoreStringRes.procedure_x,
                            formatArgs = arrayOf(state.procedureCount)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    CalcButton(
                        text = "A",
                        onClick = addAlpha
                    )
                }
                item {
                    CalcButton(
                        text = "C",
                        onClick = addCharlie
                    )
                }
                item {
                    CalcButton(
                        text = "D",
                        onClick = addDelta
                    )
                }
                item {
                    CalcButton(
                        text = "M", isBad = true,
                        onClick = addMiss
                    )
                }
                item {
                    CalcButton(
                        text = "NS", isBad = true,
                        onClick = addNoShoot
                    )
                }
                item {
                    CalcButton(
                        text = "P", isBad = true,
                        onClick = addProcedure
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = save,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = CoreStringRes.save),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = reset,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = CoreStringRes.reset),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalcButton(
    text: String,
    isBad: Boolean = false,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.size(50.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}


@Preview
@Composable
private fun CalculateHFPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            CalculateHFScreen(
                state = CalculateHFScreenState(
                    time = 23395,
                    alphaCount = 5,
                    charlieCount = 2,
                    deltaCount = 2,
                    missCount = 1
                ),
            )
        }
    }
}