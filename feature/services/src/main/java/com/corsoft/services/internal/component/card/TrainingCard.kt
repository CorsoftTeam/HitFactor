package com.corsoft.services.internal.component.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.corsoft.services.internal.model.TrainingModel
import com.corsoft.ui.theme.HitFactorTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingCard(
    modifier: Modifier = Modifier,
    trainingModel: TrainingModel,
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Тренировка ${trainingModel.dateTime.dayOfMonth} ${ //TODO: String resource
                        trainingModel.dateTime.month.getDisplayName(
                            TextStyle.FULL,
                            Locale.getDefault()
                        )
                    }",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = trainingModel.dateTime.toLocalTime().format(
                        DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(painter = painterResource(
                id = CoreDrawableRes.ic_next),
                contentDescription = ""
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(apiLevel = 34)
@Composable
private fun ServiceCardPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            TrainingCard(
                modifier = Modifier.padding(8.dp),
                trainingModel = TrainingModel(
                    dateTime = LocalDateTime.now(),
                    length = 2
                )
            ) { }
        }
    }
}