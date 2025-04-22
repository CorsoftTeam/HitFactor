package com.corsoft.services.internal.screen.calculate_hf

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun CalculateHFScreen(
    navigator: DestinationsNavigator,
    viewModel: CalculateHFViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalculateHFScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() }
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
            modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hit Factor",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "2.34",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 78.sp,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Общее время:",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "00:56.345",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "6 Alpha")
                    Text(text = "3 Charlie")
                    Text(text = "0 Delta")
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "Miss 0")
                    Text(text = "No shoot 1")
                    Text(text = "Procedure 0")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    calcButton(text = "A") { }
                }
                item {
                    calcButton(text = "C") { }
                }
                item {
                    calcButton(text = "D") { }
                }
                item {
                    calcButton(text = "M", true) { }
                }
                item {
                    calcButton(text = "NS", true) { }
                }
                item {
                    calcButton(text = "P", true) { }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(width = 1.dp, color = Color.Red)
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
private fun calcButton(
    text: String,
    isBad: Boolean = false,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.size(50.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(
            width = 1.dp, color = if (isBad)
                Color.Red
            else
                MaterialTheme.colorScheme.primary
        )
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
                state = CalculateHFScreenState(),
            )
        }
    }
}