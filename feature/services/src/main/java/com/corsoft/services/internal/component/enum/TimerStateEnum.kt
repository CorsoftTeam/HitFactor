package com.corsoft.services.internal.component.enum

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corsoft.resources.CoreStringRes

internal enum class TimerStateEnum {
    STOPPED,
    WAITING,
    RUN,
    ;

    @Composable
    fun getButtonText(readyTimerValue: Int): String =
        when (this) {
            STOPPED -> stringResource(id = CoreStringRes.stopped_state_text)
            WAITING -> readyTimerValue.toString()
            RUN -> stringResource(id = CoreStringRes.run_state_text)
        }

    val buttonColor: Color
        @Composable
        get() =
            when (this) {
                STOPPED -> MaterialTheme.colorScheme.primary
                WAITING -> MaterialTheme.colorScheme.secondary
                RUN -> Color.Red
            }

    val textSize: TextUnit
        @Composable
        get() =
            when (this) {
                WAITING -> 60.sp
                else -> 32.sp
            }

}