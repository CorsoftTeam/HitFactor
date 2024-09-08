package com.corsoft.ui.components.text_field

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: String = "",
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HFTextFieldPreview() {
    HitFactorTheme {
        Surface {
            Column {
                HFTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин",
                    text = "Логин"
                ) {}
                HFTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин"
                ) {}
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun HFTextFieldPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            Column {
                HFTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин",
                    text = "Логин"
                ) {}
                HFTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин"
                ) {}
            }
        }
    }
}