package com.corsoft.services.internal.component.card

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.internal.model.RangeModel
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.theme.HitFactorTheme

@Composable
internal fun RangeCard(
    modifier: Modifier = Modifier,
    rangeModel: RangeModel,
    onCallClick: (String) -> Unit = {},
    onWebsiteClick: (String) -> Unit = {},
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
                    text = rangeModel.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = rangeModel.website,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = rangeModel.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Image(
                painter = rememberAsyncImagePainter(
                    model = rangeModel.photo,
                    placeholder = painterResource(id = CoreDrawableRes.default_profile_photo),
                    error = painterResource(id = CoreDrawableRes.default_profile_photo),
                ),
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            HFButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = CoreStringRes.call)
            ) {
                onCallClick(rangeModel.phone)
            }
            Spacer(modifier = Modifier.width(16.dp))
            HFButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = CoreStringRes.website),
                isPrimary = false,
                customColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                onWebsiteClick(rangeModel.website)
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
            RangeCard(
                modifier = Modifier.padding(8.dp),
                rangeModel = RangeModel(
                    name = "Антей",
                    phone = "+7 800 555-35-35",
                    website = "https://antey.club/"
                )
            )
        }
    }
}