package com.corsoft.services.internal.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.services.internal.component.card.GunCard
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun GunList(
    modifier: Modifier = Modifier,
    gunList: List<GunModel>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(gunList) { item ->
            GunCard(gunModel = item) { }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            GunList(
                gunList = listOf(
                    GunModel(
                        name = "Сайга-9",
                        gunType = GunTypeEnum.PCC,
                        serialNumber = "MK6630P",
                        caliber = "9x19 FMJ"
                    ),
                    GunModel(
                        name = "МР-79-9ТМ",
                        gunType = GunTypeEnum.SELF_DEFENCE,
                        serialNumber = "Т0188-211",
                        caliber = "9PA"
                    ),
                    GunModel(
                        name = "ТОЗ-34Р",
                        gunType = GunTypeEnum.SHOTGUN,
                        serialNumber = "УМ4999",
                        caliber = "12x70"
                    )
                )
            )
        }
    }
}