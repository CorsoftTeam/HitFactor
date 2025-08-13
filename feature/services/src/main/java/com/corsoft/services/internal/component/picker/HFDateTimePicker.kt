package com.corsoft.services.internal.component.picker

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.theme.HitFactorTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HFDateTimePicker(
    onDateSelect: (LocalDateTime) -> Unit = {},
    onDismiss: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    val dateState = rememberDatePickerState()
    val timeState = rememberTimePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = CoreStringRes.cancel))
                }
            },
            tonalElevation = 0.dp,
            confirmButton = {
                TextButton(onClick = {
                    dateState.selectedDateMillis?.let {
                        selectedDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                    showTimePicker = true
                }) {
                    Text(stringResource(id = CoreStringRes.ok))
                }
            }
        ) {
            DatePicker(
                state = dateState
            )
        }
    }

    if (showTimePicker) {

        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(id = CoreStringRes.cancel))
                }
            },
            tonalElevation = 0.dp,
            confirmButton = {
                TextButton(onClick = {
                    selectedTime = LocalTime.of(timeState.hour, timeState.minute)
                    showTimePicker = false
                    val resultDateTime = LocalDateTime.of(selectedDate, selectedTime)
                    onDateSelect(resultDateTime)
                }) {
                    Text(stringResource(id = CoreStringRes.ok))
                }
            },
            text = {
                TimePicker(
                    state = timeState,
                    colors = TimePickerDefaults.colors(
                        containerColor = Color(0xFF121212)
                    )
                )
            }
        )
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    HitFactorTheme {
        Surface {
            HFDateTimePicker {

            }
        }
    }
}