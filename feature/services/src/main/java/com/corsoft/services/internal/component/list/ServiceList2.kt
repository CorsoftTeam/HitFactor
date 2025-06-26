package com.corsoft.services.internal.component.list

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.corsoft.services.internal.component.card.ServiceCard2
import com.corsoft.services.internal.component.enum.ServicesEnum
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

@Composable
internal fun ServiceList2(
    modifier: Modifier = Modifier,
    context: Context,
    navigator: DestinationsNavigator
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ServicesEnum.entries) { item ->
            ServiceCard2(
                name = item.getName(),
                description = item.getDescription(),
                icon = item.getIconRes(),
                enabled = item.isEnabled()
            ) {
                item.navigate(navigator, context)
            }
        }
    }
}

@Preview
@Composable
private fun ServiceList2Preview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            ServiceList2(
                navigator = rememberNavController().rememberDestinationsNavigator(),
                context = LocalContext.current
            )
        }
    }
}