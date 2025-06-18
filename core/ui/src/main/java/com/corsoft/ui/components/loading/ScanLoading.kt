package com.corsoft.ui.components.loading

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.corsoft.resources.CoreRawRes

@Composable
fun ScanLoading() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec
            .RawRes(resId = CoreRawRes.lottie_loading_circle)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .size(300.dp)
    )
}