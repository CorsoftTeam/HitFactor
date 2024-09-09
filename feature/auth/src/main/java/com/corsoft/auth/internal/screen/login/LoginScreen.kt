package com.corsoft.auth.internal.screen.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.auth.navigation.AuthNavGraph
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFTextButton
import com.corsoft.ui.components.text_field.HFTextField
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<AuthNavGraph>(start = true)
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold {
        LoginScreen(
            state = uiState,
            onLoginClick = { viewModel.login() },
            onRegisterClick = { })
    }
}

@Composable
private fun LoginScreen(
    state: LoginScreenModel,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
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
                placeholder = "Логин"
            ) {}
            HFTextField(
                placeholder = "Пароль"
            ) {}
            HFTextButton(
                text = "Восстановление пароля"
            ) {

            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HFButton(
                text = "Вход"
            )
            { onLoginClick }
            HFButton(
                isPrimary = false,
                text = "Регистрация"
            )
            { onRegisterClick }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreviewLight() {
    HitFactorTheme {
        LoginScreen(
            LoginScreenModel()
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