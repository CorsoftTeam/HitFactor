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
import com.corsoft.services.internal.component.card.TrainerCard
import com.corsoft.services.internal.model.TrainerModel
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun TrainerList(
    modifier: Modifier = Modifier,
    trainerList: List<TrainerModel>,
    onCallClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(trainerList) { item ->
            TrainerCard(
                trainerModel = item,
                onCallClick = onCallClick
            )
        }
    }
}

@Preview
@Composable
private fun RangeListPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TrainerList(
                trainerList = listOf(

                )
            )
        }
    }
}