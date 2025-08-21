package com.corsoft.services.internal.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.common.formatTime
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun ResultCard(
    modifier: Modifier = Modifier,
    result: ResultEntity,
    onDelete: (Long) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = result.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "%.2f".format(result.hitFactor),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = formatTime(result.time.toInt()),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(CoreStringRes.x_score, result.score.toString()),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            HFIconButton(
                icon = CoreDrawableRes.ic_trash,
                tint = MaterialTheme.colorScheme.errorContainer
            ) {
                onDelete(result.id)
            }
        }
    }
}

@Preview
@Composable
private fun RangeCardPreview2() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            ResultCard(
                modifier = Modifier.padding(8.dp),
                result = ResultEntity(
                    name = "Name Surname",
                    score = 60,
                    time = 12344,
                    hitFactor = 2.33f
                )
            )
        }
    }
}