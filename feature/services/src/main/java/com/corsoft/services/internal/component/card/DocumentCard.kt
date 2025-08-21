package com.corsoft.services.internal.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.HitFactorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DocumentCard(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val tintColor =
        if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    val textTintColor =
        if (enabled) White else MaterialTheme.colorScheme.onSurfaceVariant
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { if (enabled) onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = textTintColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                painter = painterResource(id = CoreDrawableRes.ic_next),
                contentDescription = "",
                tint = textTintColor
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun ServiceCard2Preview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            DocumentCard(
                modifier = Modifier.padding(8.dp),
                name = "Таймер",
                description = "Управляйте личным арсеналом, следите за состоянием оружия и вовремя проводите чистку",
            ) { }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun ServiceCard2Preview2() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            DocumentCard(
                modifier = Modifier.padding(8.dp),
                name = "Таймер",
                description = "Управляйте личным арсеналом, следите за состоянием оружия и вовремя проводите чистку",
                enabled = false,
            ) { }
        }
    }
}