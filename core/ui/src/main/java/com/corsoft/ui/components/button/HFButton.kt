package com.corsoft.ui.components.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFButton(
    modifier: Modifier = Modifier,
    text: String,
    isPrimary: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isPrimary) {
                true -> AppColors.Primary
                false -> AppColors.Secondary
            },
            contentColor = when (isPrimary) {
                true -> AppColors.TextPrimaryDark
                false -> AppColors.TextPrimaryDark
            }
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun HFButtonPreview() {
    HitFactorTheme {
        Surface {
            Column {
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    enabled = false,
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    isPrimary = false,
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    enabled = false,
                    isPrimary = false,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun HFButtonPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            Column {
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    enabled = false,
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    isPrimary = false,
                    onClick = {}
                )
                HFButton(
                    modifier = Modifier.padding(8.dp),
                    text = "Кнопка",
                    enabled = false,
                    isPrimary = false,
                    onClick = {}
                )
            }
        }
    }
}