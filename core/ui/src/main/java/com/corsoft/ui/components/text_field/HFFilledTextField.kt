package com.corsoft.ui.components.text_field

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFFilledTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: String = "",
    onTextChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        shape = RoundedCornerShape(16.dp)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HFFiledTextFieldPreview() {
    HitFactorTheme {
        Surface {
            Column {
                HFFilledTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин",
                    text = "Логин"
                ) {}
                HFFilledTextField(
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
private fun HFFilledTextFieldPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            Column {
                HFFilledTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин",
                    text = "Логин"
                ) {}
                HFFilledTextField(
                    modifier = Modifier.padding(8.dp),
                    placeholder = "Логин"
                ) {}
            }
        }
    }
}