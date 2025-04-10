package com.corsoft.services.internal.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.corsoft.services.internal.component.card.ServiceCard
import com.corsoft.services.internal.component.enum.ServicesEnum
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

@Composable
internal fun ServiceList(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ServicesEnum.entries) { item ->
            ServiceCard(name = item.getName(), icon = item.getIconRes()) {
                item.navigate(navigator)
            }
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
            ServiceList(
                navigator = rememberNavController().rememberDestinationsNavigator()
            )
        }
    }
}