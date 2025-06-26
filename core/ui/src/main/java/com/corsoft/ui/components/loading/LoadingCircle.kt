import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.ui.theme.HitFactorTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingCircle(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .size(150.dp)
            .shimmer(),
        painter = painterResource(id = CoreDrawableRes.logo_large),
        contentDescription = ""
    )
}

@Preview
@Composable
private fun LoadingCirclePreview() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            LoadingCircle()
        }
    }
}