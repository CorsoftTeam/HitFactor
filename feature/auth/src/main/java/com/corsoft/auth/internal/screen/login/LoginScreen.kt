package com.corsoft.auth.internal.screen.login

import LoadingCircle
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.auth.api.AuthNavGraph
import com.corsoft.auth.api.AuthNavigator
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFTextButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.text_field.HFTextField
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<AuthNavGraph>(start = true)
internal fun LoginScreen(
    navigator: DestinationsNavigator,
    authNavigator: AuthNavigator,
    viewModel: LoginViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is LoginEffect.Login -> authNavigator.login()
            is LoginEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            HFSnackBarHost(
                hostState = snackBarHostState,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        if (uiState.isLoading) {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingCircle()
            }
        } else {
            LoginScreen(
                state = uiState,
                onLoginClick = { viewModel.onAction(LoginAction.Login) },
                onRegisterClick = { navigator.navigate(RegisterScreenDestination) },
                onLoginChange = { viewModel.onAction(LoginAction.UpdateLogin(it)) },
                onPasswordChange = { viewModel.onAction(LoginAction.UpdatePassword(it)) }
            )
        }
    }
}

@Composable
private fun LoginScreen(
    state: LoginScreenModel,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onLoginChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = CoreDrawableRes.logo_large),
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HFTextField(
                placeholder = stringResource(id = CoreStringRes.login),
                text = state.login,
                onTextChange = { onLoginChange(it) }
            )
            HFTextField(
                placeholder = stringResource(id = CoreStringRes.password),
                text = state.password,
                onTextChange = { onPasswordChange(it) }
            )
            HFTextButton(
                text = stringResource(id = CoreStringRes.restore_pass)
            ) {

            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HFButton(
                text = stringResource(id = CoreStringRes.enter)
            )
            { onLoginClick() }
            HFButton(
                isPrimary = false,
                text = stringResource(id = CoreStringRes.register)
            )
            { onRegisterClick() }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreviewLight() {
    HitFactorTheme {
        LoginScreen(
            LoginScreenModel( isLoading = true)
        )
    }
}

@Preview
@Composable
fun LoginScreenPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        LoginScreen(
            LoginScreenModel()
        )
    }
}