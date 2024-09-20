package com.corsoft.auth.internal.screen.register

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.ramcosta.composedestinations.generated.auth.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<AuthNavGraph>
internal fun RegisterScreen(
    navigator: DestinationsNavigator,
    authNavigator: AuthNavigator,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is RegisterEffect.Register -> authNavigator.login()
            is RegisterEffect.ShowError -> scope.launch {
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
        RegisterScreen(
            state = uiState,
            onLoginClick = { navigator.navigate(LoginScreenDestination) },
            onRegisterClick = { viewModel.onAction(RegisterAction.Register) },
            onLoginChange = { viewModel.onAction(RegisterAction.UpdateLogin(it)) },
            onPasswordChange = { viewModel.onAction(RegisterAction.UpdatePassword(it)) },
            onNameChange = { viewModel.onAction(RegisterAction.UpdateName(it)) },
            onPhoneChange = { viewModel.onAction(RegisterAction.UpdatePhone(it)) }
        )
    }
}

@Composable
private fun RegisterScreen(
    state: RegisterScreenState,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onLoginChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
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
                text = state.login
            ) { onLoginChange(it) }
            HFTextField(
                placeholder = stringResource(id = CoreStringRes.password),
                text = state.password
            ) { onPasswordChange(it) }
            HFTextField(
                placeholder = stringResource(id = CoreStringRes.name),
                text = state.name
            ) { onNameChange(it) }
            HFTextField(
                placeholder = stringResource(id = CoreStringRes.phone_number),
                text = state.phone
            ) { onPhoneChange(it) }
            HFTextButton(
                text = stringResource(id = CoreStringRes.already_reg)
            ) { onLoginClick() }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HFButton(
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
        RegisterScreen(
            RegisterScreenState()
        )
    }
}

@Preview
@Composable
fun LoginScreenPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        RegisterScreen(
            RegisterScreenState()
        )
    }
}