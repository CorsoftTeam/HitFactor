package com.corsoft.hitfactor.data.ballistic.internal.mapper

fun metersToYards(m: Double) = m * 1.0936133
fun yardsToMeters(yd: Double) = yd * 0.9144

fun msToFps(ms: Double) = ms * 3.28084
fun fpsToMs(fps: Double) = fps * 0.3048

fun metersToFeet(m: Double) = m * 3.28084
fun feetToMeters(ft: Double) = ft * 0.3048

fun inchesToMeters(inches: Double) = inches * 0.0254
fun metersToInches(m: Double) = m / 0.0254

fun inchesToCentimeters(inches: Double) = inches * 2.54
fun centimetersToInches(cm: Double) = cm / 2.54
