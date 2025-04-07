package com.corsoft.services.internal.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.theme.HitFactorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ServiceCard(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Card (
        modifier = modifier.size(100.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
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
