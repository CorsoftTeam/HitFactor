package com.corsoft.services.internal.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corsoft.services.internal.component.enum.TimerStateEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimerButton (
    modifier: Modifier = Modifier,
    timerState: TimerStateEnum,
    readyTimerValue: Int,
    onStartButtonClick: () -> Unit,
    onCountHitFactorClick: () -> Unit,
) {
    Card(
        onClick = onStartButtonClick,
        modifier = modifier.fillMaxWidth().height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = timerState.buttonColor
        ),
        shape = RoundedCornerShape(100.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = timerState.getButtonText(readyTimerValue = readyTimerValue),
                fontSize = timerState.textSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}