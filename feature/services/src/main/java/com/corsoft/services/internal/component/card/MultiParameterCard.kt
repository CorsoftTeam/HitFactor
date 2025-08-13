package com.corsoft.services.internal.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun MultiParameterCard(
    modifier: Modifier = Modifier,
    params: Map<String, String>,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            params.keys.forEach { key ->
                Row {
                    Text(text = "$key:", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = params.getOrDefault(key, ""),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 34)
private fun MultiParameterCardPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            MultiParameterCard(
                params = mapOf(
                    Pair("param1", "value1"),
                    Pair("param2", "value2"),
                    Pair("param3", "value3")
                )
            )
        }
    }
}