package com.corsoft.ui.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = AppColors.TextBlue,
        modifier = modifier.clickable { onClick() }
    )
}

@Preview
@Composable
private fun HFTextButtonPreview() {
    HitFactorTheme {
        Surface {
            HFTextButton(
                text = "Восстановить пароль",
                modifier = Modifier.padding(8.dp)
            ) { }
        }
    }
}