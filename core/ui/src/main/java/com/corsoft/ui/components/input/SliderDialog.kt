package com.corsoft.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.theme.HitFactorTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderDialog(
    modifier: Modifier = Modifier,
    value: Int = 0,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit,
) {
    var value by remember { mutableFloatStateOf(value.toFloat()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = value.roundToInt().toString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                Slider(
                    value = value,
                    onValueChange = { newValue -> value = newValue },
                    valueRange = 1f..100f,
                    steps = 99,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            stringResource(CoreStringRes.cancel),
                            color = MaterialTheme.colorScheme.errorContainer
                        )
                    }
                    Button(
                        onClick = { onConfirm(value) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text(stringResource(CoreStringRes.ok))
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    HitFactorTheme {
        SliderDialog(
            title = "Чувствительность",
            onDismiss = {}
        ) { }
    }
}