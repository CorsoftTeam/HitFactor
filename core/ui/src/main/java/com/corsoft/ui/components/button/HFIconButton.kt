package com.corsoft.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = tint
        )
    }
}

@Preview
@Composable
private fun HFIconButtonPreview() {
    HitFactorTheme {
        Surface {
            HFIconButton(icon = CoreDrawableRes.ic_settings) { }
        }
    }
}