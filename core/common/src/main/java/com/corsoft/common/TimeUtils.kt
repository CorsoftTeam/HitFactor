package com.corsoft.common

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatTime(milliseconds: Int): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val millis = milliseconds % 1000
    return String.format("%02d:%02d.%03d", minutes, seconds, millis)
}