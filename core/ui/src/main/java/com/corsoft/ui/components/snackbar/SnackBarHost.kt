package com.corsoft.ui.components.snackbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.corsoft.resources.CoreStringRes
import kotlinx.coroutines.launch

private const val MAX_SYMBOLS = 100
private const val DURATION_ANIMATION = 500

@Composable
fun HFSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val error = stringResource(id = CoreStringRes.something_wrong)
    hostState.currentSnackbarData?.visuals?.message?.let {
        if (it.length > MAX_SYMBOLS) {
            hostState.currentSnackbarData?.dismiss()
            scope.launch {
                hostState.showSnackbar(error)
            }
        }
    }

    SnackbarHost(
        hostState = hostState,
        modifier = modifier

    ) { snackbarData: SnackbarData ->
        CustomSnackbar(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec =
                    TweenSpec(durationMillis = DURATION_ANIMATION)
                ),
            shape = RectangleShape,
            snackbarData = snackbarData,
            textAlign = TextAlign.Center
        )
    }
}
