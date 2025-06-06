package com.corsoft.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.AppColors.Primary
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun WarningCard(
    modifier: Modifier = Modifier,
    text: String,
    isStrong: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(0.5.dp, if (isStrong) Color.Red else Primary)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            ) {
            Icon(
                painter = painterResource(id = CoreDrawableRes.ic_alert),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = if (isStrong) Color.Red else Primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun WarningCardPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            WarningCard(
                modifier = Modifier.padding(8.dp),
                text = "Обратите внимание, что документы хранятся локально на вашем устройстве и не передаются на сервер"
            )
        }
    }
}