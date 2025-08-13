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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun HFButton(
    modifier: Modifier = Modifier,
    text: String,
    isPrimary: Boolean = true,
    enabled: Boolean = true,
    customColor: Color? = null,
    isShimmer: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(16.dp),
        modifier = if (isShimmer)
            modifier.fillMaxWidth().shimmer(
                customShimmer = rememberShimmer(
                    shimmerBounds = ShimmerBounds.Window,
                    theme = defaultShimmerTheme.copy(
                        blendMode = BlendMode.SrcAtop,
                        shaderColors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0f),
                        ),
                    )
                )
            )
        else
            modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = customColor
                ?: when (isPrimary) {
                    true -> AppColors.Primary
                    false -> MaterialTheme.colorScheme.secondary
                }
        )
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

@Preview(apiLevel = 34)
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