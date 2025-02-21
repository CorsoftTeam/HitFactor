import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.ui.theme.HitFactorTheme

@Composable
fun LoadingCircle(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Float = 8f,
    durationMillis: Int = 1000
) {
    // Анимация для угла поворота
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    // Рисуем кружок загрузки
    Canvas(
        modifier = modifier.size(80.dp) // Размер кружочка
    ) {
        drawArc(
            color = color,
            startAngle = angle,
            sweepAngle = 270f, // Угол дуги (можно настроить)
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Preview
@Composable
private fun LoadingCirclePreview(){
    HitFactorTheme {
        Surface {
            LoadingCircle()
        }
    }
}