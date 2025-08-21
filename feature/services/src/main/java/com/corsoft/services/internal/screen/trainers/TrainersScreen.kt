package com.corsoft.services.internal.screen.trainers

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.list.TrainerList
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.dropdown.HFDropdownMenu
import com.corsoft.ui.components.placeholder.HFPlaceholder
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun TrainersScreen(
    navigator: DestinationsNavigator,
    viewModel: TrainersViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onAction(TrainersAction.Refresh)
    }

    TrainersScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        onCityChange = { viewModel.onAction(TrainersAction.ChangeCity(it)) },
        onWebsiteClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(it)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        },
        onCallClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$it")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun TrainersScreen(
    modifier: Modifier = Modifier,
    state: TrainersScreenState,
    onCityChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onWebsiteClick: (String) -> Unit = {},
    onCallClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.trainers)
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = onBackClick
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            InfoCard(
                text = stringResource(CoreStringRes.add_range_or_instructor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HFDropdownMenu(
                selectedOption = state.currentCity.name,
                options = state.citiesList.map { it.name },
                onValueChange = onCityChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.trainersList.isEmpty()) {
                HFPlaceholder(
                    text = stringResource(CoreStringRes.select_your_city)
                )
            } else {
                TrainerList(
                    trainerList = state.trainersList,
                    onCallClick = onCallClick
                )
            }
        }
    }
}


@Preview
@Composable
private fun ServicesPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TrainersScreen(
                state = TrainersScreenState()
            )
        }
    }
}