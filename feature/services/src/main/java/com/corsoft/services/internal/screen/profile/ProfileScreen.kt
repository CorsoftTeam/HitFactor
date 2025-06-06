package com.corsoft.services.internal.screen.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.card.ParameterCard
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = uiState.user.name
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
        ProfileScreen(
            modifier = Modifier.padding(paddingValues),
            state = uiState,
        )
    }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    onSettingsClick: () -> Unit = {},
) {
    Column (
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = state.profilePhoto,
                    placeholder = painterResource(id = CoreDrawableRes.default_profile_photo),
                    error = painterResource(id = CoreDrawableRes.default_profile_photo),
                ),
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ParameterCard(
            name = stringResource(id = CoreStringRes.login),
            value = state.user.login
        )
        Spacer(modifier = Modifier.height(16.dp))
        ParameterCard(
            name = stringResource(id = CoreStringRes.email),
            value = state.user.email
        )
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun ProfileScreenPreview() {
    HitFactorTheme {
        Scaffold {
            ProfileScreen(
                state = ProfileScreenState(

                )
            )
        }
    }
}