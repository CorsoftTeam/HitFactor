package com.corsoft.services.internal.screen.settings

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.ClickableCard
import com.corsoft.services.internal.component.enum.ServicesGroupsEnum
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.dropdown.HFDropdownMenu
import com.corsoft.ui.components.topbar.ToolBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.settings)
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = { navigator.popBackStack() }
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(CoreStringRes.services)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HFDropdownMenu(
                    options = ServicesGroupsEnum.entries.map { it.groupName },
                    selectedOption = uiState.value.serviceGroup.groupName,
                    onValueChange = {
                        viewModel.onAction(SettingsAction.OnServiceGroupChange(it))
                    }
                )
            }

            Column {
                Text(
                    text = stringResource(CoreStringRes.subscription)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ClickableCard(
                    text = stringResource(CoreStringRes.subscription_settings)
                ) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "rustore://profile/subscriptions".toUri()
                        )
                    )
                }
            }

            Column {
                Text(
                    text = stringResource(CoreStringRes.about_app)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ClickableCard(
                    text = stringResource(CoreStringRes.privacy_policy)
                ) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "https://disk.yandex.ru/i/X6FlP2yhWpFG-Q".toUri()
                        )
                    )
                }
            }
        }
    }
}