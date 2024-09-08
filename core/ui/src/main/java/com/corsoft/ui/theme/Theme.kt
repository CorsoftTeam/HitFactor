package com.corsoft.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.Primary,
    secondary = AppColors.Secondary,
    background = DarkThemeColors.Background,
    surface = DarkThemeColors.Surface,
    error = DarkThemeColors.Error,
    onPrimary = DarkThemeColors.OnPrimary,
    onSecondary = DarkThemeColors.OnSecondary,
    onBackground = DarkThemeColors.OnBackground,
    onSurface = DarkThemeColors.OnSurface,
    onError = DarkThemeColors.OnError,
    onSurfaceVariant = AppColors.TextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    secondary = AppColors.Secondary,
    background = LightThemeColors.Background,
    surface = LightThemeColors.Surface,
    error = LightThemeColors.Error,
    onPrimary= LightThemeColors.OnPrimary,
    onSecondary= LightThemeColors.OnSecondary,
    onBackground= LightThemeColors.OnBackground,
    onSurface= LightThemeColors.OnSurface,
    onError= LightThemeColors.OnError,
    onSurfaceVariant = AppColors.TextSecondaryLight
)

@Composable
fun HitFactorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}