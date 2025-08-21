package com.corsoft.services.internal.screen.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.ResultCard
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.placeholder.HFPlaceholder
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun ResultsScreen(
    navigator: DestinationsNavigator,
    viewModel: ResultsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResultsScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        onDelete = { viewModel.onAction(ResultsAction.OnDelete(it)) }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun ResultsScreen(
    modifier: Modifier = Modifier,
    state: ResultsScreenState,
    onBackClick: () -> Unit = {},
    onDelete: (Long) -> Unit = {}
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
                                text = stringResource(id = CoreStringRes.results)
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.results.isEmpty()) {
                HFPlaceholder(
                    text = stringResource(CoreStringRes.there_are_results)
                )
            } else {
                state.results.forEach { resultEntity ->
                    ResultCard(
                        result = resultEntity,
                        onDelete = onDelete
                    )
                }
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
            ResultsScreen(
                state = ResultsScreenState()
            )
        }
    }
}