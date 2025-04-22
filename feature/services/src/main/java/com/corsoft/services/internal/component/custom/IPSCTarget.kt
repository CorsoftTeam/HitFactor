package com.corsoft.services.internal.component.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import com.corsoft.ui.theme.HitFactorTheme

data class ShotPoint(val x: Float, val y: Float, val zone: Zone)
enum class Zone { ALPHA, CHARLIE, DELTA, MISS }

fun Modifier.tapWithPosition(
    enabled: Boolean = true,
    onTap: (x: Float, y: Float) -> Unit
) = composed {
    val density = LocalDensity.current
    Modifier.pointerInput(enabled) {
        if (!enabled) return@pointerInput

        detectTapGestures { offset ->
            with(density) {
                val x = offset.x.toDp().value / size.width.toDp().value
                val y = offset.y.toDp().value / size.height.toDp().value
                onTap(x, y)
            }
        }
    }
}

@Composable
fun IPSCTarget(
    shots: List<ShotPoint>,
    onTargetClick: (Float, Float) -> Unit
) {
    val color = MaterialTheme.colorScheme.primary
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .tapWithPosition { x, y ->
                {
                    //TODO
                }
            }
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2
        val targetWidth = 300 * 2  // 80% экрана
        val targetHeight = 375 * 2  // Соотношение 45x75 см
        val cornerCutSizeWidth = targetWidth / 3 // Размер среза углов (~15 см)
        val cornerCutSizeHeight = targetHeight / 3 // Размер среза углов (~15 см)

        // Координаты углов мишени (до срезов)
        val left = centerX - targetWidth / 2
        val top = centerY - targetHeight / 2
        val right = centerX + targetWidth / 2
        val bottom = centerY + targetHeight / 2

        // Путь для внешнего контура (со срезами)
        val targetPath = Path().apply {
            moveTo(left + cornerCutSizeWidth, top)
            lineTo(right - cornerCutSizeWidth, top)
            lineTo(right, top + cornerCutSizeHeight)
            lineTo(right, bottom - cornerCutSizeHeight)
            lineTo(right - cornerCutSizeWidth, bottom)
            lineTo(left + cornerCutSizeWidth, bottom)
            lineTo(left, bottom - cornerCutSizeHeight)
            lineTo(left, top + cornerCutSizeHeight)
            close()
        }

        val targetPath2 = Path().apply {
            moveTo(left + cornerCutSizeWidth, top)
            lineTo(right - cornerCutSizeWidth, top)
            lineTo(right - 50 * 2, top + cornerCutSizeHeight)
            lineTo(right - 50 * 2, bottom - cornerCutSizeHeight - 30 * 2)
            lineTo(right - cornerCutSizeWidth - 15 * 2, bottom - 75 * 2)
            lineTo(left + cornerCutSizeWidth + 15 * 2, bottom - 75 * 2)
            lineTo(left + 50 * 2, bottom - cornerCutSizeHeight - 30 * 2)
            lineTo(left + 50 * 2, top + cornerCutSizeHeight)
            close()
        }

        val targetPath3 = Path().apply {
            moveTo(left + cornerCutSizeWidth + 30 * 2, top + 15 * 2)
            lineTo(right - cornerCutSizeWidth - 30 * 2, top + 15 * 2)
            lineTo(right - 100 * 2, top + cornerCutSizeHeight)
            lineTo(right - 100 * 2, bottom - cornerCutSizeHeight - 70 * 2)
            lineTo(right - cornerCutSizeWidth - 30 * 2, bottom - 75 * 2 - 70 * 2)
            lineTo(left + cornerCutSizeWidth + 30 * 2, bottom - 75 * 2 - 70 * 2)
            lineTo(left + 100 * 2, bottom - cornerCutSizeHeight - 70 * 2)
            lineTo(left + 100 * 2, top + cornerCutSizeHeight)
            close()
        }

        // Отрисовка зон
        drawPath(targetPath, color, style = Stroke(width = 5f))  // Delta
        drawPath(targetPath2, color, style = Stroke(width = 5f))  // Charlie
        drawPath(targetPath3, color, style = Stroke(width = 5f)) // Alpha

        drawText(
            textMeasurer = textMeasurer,
            text = "D        C              A              C        D",
            style = TextStyle(color = color),
            topLeft = Offset(left + 40, top + 270)
        )


        // Попадания (точки)
        shots.forEach { shot ->
            val pos = Offset(shot.x * width, shot.y * height)
            drawCircle(
                color = when (shot.zone) {
                    Zone.ALPHA -> Color.Red
                    Zone.CHARLIE -> Color.Blue
                    Zone.DELTA -> Color.Green
                    Zone.MISS -> Color.Gray
                },
                radius = 15f,
                center = pos
            )
        }
    }
}

@Preview
@Composable
private fun TargetPreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            IPSCTarget(
                shots = listOf(),
                onTargetClick = { f1, f2 -> }
            )
        }
    }
}

