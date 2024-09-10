package com.corsoft.ui.components.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    textAlign: TextAlign? = null,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionColor: Color = SnackbarDefaults.actionColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp),
    onDismiss: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    val actionLabel = snackbarData.visuals.actionLabel
    val actionComposable = createActionComposable(
        actionLabel = actionLabel,
        actionColor = actionColor,
        onActionClick = onActionClick,
        snackbarData = snackbarData
    )

    val dismissActionComposable = createDismissActionComposable(
        snackbarData = snackbarData,
        onDismiss = onDismiss,
        dismissActionContentColor = dismissActionContentColor
    )
    Snackbar(
        modifier = modifier,
        action = actionComposable,
        dismissAction = dismissActionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
        content = {
            Text(
                snackbarData.visuals.message,
                textAlign = textAlign,
                color = contentColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            )
        }
    )
}

private fun createActionComposable(
    actionLabel: String?,
    actionColor: Color,
    onActionClick: () -> Unit,
    snackbarData: SnackbarData
): (@Composable () -> Unit)? {
    return actionLabel?.let {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                onClick = {
                    onActionClick()
                    snackbarData.performAction()
                },
                content = { Text(actionLabel) }
            )
        }
    }
}

private fun createDismissActionComposable(
    snackbarData: SnackbarData,
    onDismiss: () -> Unit,
    dismissActionContentColor: Color
): (@Composable () -> Unit)? {
    return if (snackbarData.visuals.withDismissAction) {
        @Composable {
            IconButton(
                onClick = {
                    onDismiss()
                    snackbarData.dismiss()
                },
                content = {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Dismiss",
                        tint = dismissActionContentColor
                    )
                }
            )
        }
    } else {
        null
    }
}