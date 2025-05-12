package com.corsoft.services.internal.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.HitFactorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ServiceCard(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val tintColor =
        if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Card (
        modifier = modifier.size(100.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(36.dp),
                tint = tintColor
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                color = tintColor
            )
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    HitFactorTheme (
        darkTheme = true
    ){
        Surface {
            ServiceCard(
                modifier = Modifier.padding(8.dp),
                name = "Таймер",
                icon = CoreDrawableRes.ic_timer_outline
            ) { }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview2() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            ServiceCard(
                modifier = Modifier.padding(8.dp),
                name = "Таймер",
                enabled = false,
                icon = CoreDrawableRes.ic_timer_outline
            ) { }
        }
    }
}
