package com.corsoft.services.internal.component.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.AppColors
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun ShotTimeItem(
    modifier: Modifier = Modifier,
    index: Int,
    time: String,
    split: String,
    onDelete: () -> Unit
){

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(36.dp),
                text = index.toString(),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = time,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "+$split",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(32.dp))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onDelete() },
                painter = painterResource(id = CoreDrawableRes.ic_trash),
                tint = AppColors.RedIcon,
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun ShotTimeItemPreview() {
    HitFactorTheme {
        Surface {
            ShotTimeItem(
                index = 1,
                time = "01:56.076",
                split = "01:56.076") { }
        }
    }
}

@Preview
@Composable
private fun ShotTimeItemPreviewDark() {
    HitFactorTheme (
        darkTheme = true
    ){
        Surface {
            ShotTimeItem(
                index = 2,
                time = "02:01.076",
                split = "00:05.076") { }
        }
    }
}