package com.corsoft.services.internal.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.theme.HitFactorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GunCard(
    modifier: Modifier = Modifier,
    gunModel: GunModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
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
                    text = gunModel.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = gunModel.serialNumber,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column (
                horizontalAlignment = Alignment.End
            ){
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = gunModel.gunType.getName(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                if (gunModel.shotCount > gunModel.shotCountBeforeClean.times(0.8f)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = CoreDrawableRes.ic_alert),
                            tint = if (gunModel.shotCount > gunModel.shotCountBeforeClean) Color.Red else Color.Yellow,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    HitFactorTheme(
        darkTheme = false
    ) {
        Surface {
            GunCard(
                modifier = Modifier.padding(8.dp),
                gunModel = GunModel(
                    name = "Сайга-9",
                    gunType = GunTypeEnum.PCC,
                    serialNumber = "MK6630P",
                    caliber = "9x19 FMJ",
                    shotCount = 12
                )
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
            GunCard(
                modifier = Modifier.padding(8.dp),
                gunModel = GunModel(
                    name = "Сайга-9",
                    gunType = GunTypeEnum.PCC,
                    serialNumber = "MK6630P",
                    caliber = "9x19 FMJ",
                    shotCount = 123
                )
            ) { }
        }
    }
}
