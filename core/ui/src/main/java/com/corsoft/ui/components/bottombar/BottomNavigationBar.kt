package com.corsoft.ui.components.bottombar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<String>,
    icons: List<Int>,
    iconsOutline: List<Int>,
    selectedItem: Int = 0,
    onItemClick: (Int) -> Unit = {},
) {

    Surface(
        modifier = modifier
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                items.forEachIndexed { index, item ->
                    key(index) {
                        BottomNavigationBarItem(
                            text = item,
                            icon = icons[index],
                            iconOutline = iconsOutline[index],
                            selected = selectedItem == index,
                            onClick = { onItemClick(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    @DrawableRes iconOutline: Int,
    selected: Boolean,
    onClick: () -> Unit = {},
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            },
        ),
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(if (selected) icon else iconOutline),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    HitFactorTheme {
        Surface {
            BottomNavigationBar(
                items = List(4) { "Item" }.toList(),
                icons = List(4) { CoreDrawableRes.ic_logo }.toList(),
                iconsOutline = List(4) { CoreDrawableRes.ic_logo }.toList(),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}