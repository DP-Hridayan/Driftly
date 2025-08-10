package `in`.hridayan.driftly.core.presentation.components.lottie

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import `in`.hridayan.driftly.R

@Composable
fun SpinningGearsLottie(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spinning_gears))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        restartOnPlay = false
    )

    val color = MaterialTheme.colorScheme.onSurface

    val colorProperty = rememberLottieDynamicProperty(
        property = LottieProperty.COLOR,
        keyPath = arrayOf("**"),
        value = color.toArgb()
    )

    val dynamicProperties = rememberLottieDynamicProperties(colorProperty)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}