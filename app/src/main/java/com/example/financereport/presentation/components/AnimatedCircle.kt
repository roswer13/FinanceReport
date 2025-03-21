package com.example.financereport.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financereport.ui.theme.FinanceReportTheme

private const val DividerLengthInDegrees = 1.8f

private enum class AnimatedCircleProgress { START, END }

@Composable
fun AnimatedCircle(
    modifier: Modifier,
    proportions: List<Float>,
    colors: List<Color>
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }

    val strokeWidth = with(LocalDensity.current) { 18.dp.toPx() }
    val transition = updateTransition(currentState, label = "")
    val angleOffset = transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = LinearEasing
            )
        },
        label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }.value

    val shift = transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 10.85f)
            )
        },
        label = ""
    ) {
        if (it == AnimatedCircleProgress.START) {
            0f
        } else {
            30f
        }
    }.value

    Canvas(modifier) {
        val innerRadius = (size.minDimension - strokeWidth) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportion ->
            val sweepAngle = proportion * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweepAngle - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style =  Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            startAngle += sweepAngle
        }
    }
}

@Preview(
    name = "Animated circle dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Animated circle light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun AnimatedCirclePreview() {
    FinanceReportTheme {
        val proportions = listOf(0.25f, 0.15f, 0.2f, 0.4f)
        val colors = listOf(
            Color(0xFFE1BEE7),
            Color(0xFFFFF59D),
            Color(0xFFB2EBF2),
            Color(0xFFC8E6C9)
        )
        AnimatedCircle(
            modifier = Modifier.fillMaxSize(),
            proportions = proportions,
            colors = colors
        )
    }
}