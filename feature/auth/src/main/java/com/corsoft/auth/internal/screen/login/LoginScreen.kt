package com.corsoft.login.screen.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFTextButton
import com.corsoft.ui.components.text_field.HFTextField
import com.corsoft.ui.theme.HitFactorTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(

) {
    Scaffold {
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
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
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
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                HFButton(
                    text = "Вход"
                )
                {}
                HFButton(
                    isPrimary = false,
                    text = "Регистрация"
                )
                {}
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreviewLight() {
    HitFactorTheme {
        LoginScreen()
    }
}

@Preview
@Composable
fun LoginScreenPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        LoginScreen()
    }
}