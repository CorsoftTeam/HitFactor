package com.corsoft.ui.components.text_field

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun HFFilledTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: String = "",
    semanticContentType: ContentType? = null,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation? = null,
    onTextChange: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = if (semanticContentType == null)
            modifier.fillMaxWidth()
        else
            modifier
                .fillMaxWidth()
                .semantics { contentType = semanticContentType },
        label = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = singleLine,
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