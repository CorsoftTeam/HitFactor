package com.corsoft.auth.internal.screen.register

import LoadingCircle
import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.corsoft.ui.components.text_field.HFFilledTextField
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.auth.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<AuthNavGraph>
internal fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is RegisterEffect.Register -> navigator.navigate(LoginScreenDestination)
            is RegisterEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    RegisterScreen(
        state = uiState,
        onLoginClick = { navigator.navigate(LoginScreenDestination) },
        onRegisterClick = { viewModel.onAction(RegisterAction.Register) },
        onLoginChange = { viewModel.onAction(RegisterAction.UpdateLogin(it)) },
        onPasswordChange = { viewModel.onAction(RegisterAction.UpdatePassword(it)) },
        onNameChange = { viewModel.onAction(RegisterAction.UpdateName(it)) },
        onPhoneChange = { viewModel.onAction(RegisterAction.UpdatePhone(it)) },
        onEmailChange = { viewModel.onAction(RegisterAction.UpdateEmail(it)) },
        snackbarHostState = snackBarHostState
    )


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
    onEmailChange: (String) -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {

    Scaffold { paddingValues ->
        SnackbarHost(
            modifier = Modifier.statusBarsPadding(),
            hostState = snackbarHostState
        )
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
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .imePadding()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    modifier = Modifier
                        .height(100.dp).shimmer(
                            customShimmer = rememberShimmer(
                                shimmerBounds = ShimmerBounds.View,
                                theme = defaultShimmerTheme.copy(
                                    animationSpec = infiniteRepeatable(
                                        animation = shimmerSpec(
                                            durationMillis = 2000,
                                            easing = LinearEasing,
                                            delayMillis = 500,
                                        ),
                                        repeatMode = RepeatMode.Restart,
                                    ),
                                    blendMode = BlendMode.SrcAtop,
                                    shaderColors = listOf(
                                        Color.White.copy(alpha = 0f),
                                        Color.White.copy(alpha = 0.3f),
                                        Color.White.copy(alpha = 0f),
                                    ),
                                    shaderColorStops = listOf(
                                        0.4f,
                                        0.5f,
                                        0.6f,
                                    ),
                                )
                            )
                        ),
                    painter = painterResource(id = CoreDrawableRes.logo_large),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    HFFilledTextField(
                        placeholder = stringResource(id = CoreStringRes.login),
                        text = state.login
                    ) { onLoginChange(it) }
                    HFFilledTextField(
                        placeholder = stringResource(id = CoreStringRes.password),
                        text = state.password,
                        semanticContentType = ContentType.Password,
                        visualTransformation = PasswordVisualTransformation()
                    ) { onPasswordChange(it) }
                    HFFilledTextField(
                        placeholder = stringResource(id = CoreStringRes.name),
                        text = state.name,
                        semanticContentType = ContentType.PersonFirstName
                    ) { onNameChange(it) }
                    HFFilledTextField(
                        placeholder = stringResource(id = CoreStringRes.email),
                        text = state.email,
                        semanticContentType = ContentType.EmailAddress
                    ) { onEmailChange(it) }
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
            RegisterScreenState(isLoading = false)
        )
    }
}