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
import com.corsoft.services.internal.component.card.RangeCard
import com.corsoft.services.internal.model.RangeModel
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun RangeList(
    modifier: Modifier = Modifier,
    rangeList: List<RangeModel>,
    onCallClick: (String) -> Unit = {},
    onWebsiteClick: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(rangeList) { item ->
            RangeCard(
                rangeModel = item,
                onCallClick = onCallClick,
                onWebsiteClick = onWebsiteClick
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
            RangeList(
                rangeList = listOf(
                    RangeModel(
                        name = "Антей",
                        phone = "+7 800 555-35-35",
                        website = "https://antey.club/"
                    ),
                    RangeModel(
                        name = "Антей",
                        phone = "+7 800 555-35-35",
                        website = "https://antey.club/"
                    ),
                    RangeModel(
                        name = "Антей",
                        phone = "+7 800 555-35-35",
                        website = "https://antey.club/"
                    )
                )
            )
        }
    }
}