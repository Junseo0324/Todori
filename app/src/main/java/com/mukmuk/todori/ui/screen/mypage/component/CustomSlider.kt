package com.mukmuk.todori.ui.screen.mypage.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier
) {
    var sliderWidth by remember { mutableStateOf(0f) }
    val leftPaddingPx = 20f
    val rightPaddingPx = 20f

    fun xToValue(x: Float): Float {
        if (sliderWidth <= leftPaddingPx + rightPaddingPx) return valueRange.start
        val usable = sliderWidth - leftPaddingPx - rightPaddingPx
        val clampedX = x.coerceIn(leftPaddingPx, sliderWidth - rightPaddingPx)
        val ratio = (clampedX - leftPaddingPx) / usable
        return (valueRange.start +
                ratio * (valueRange.endInclusive - valueRange.start))
            .coerceIn(valueRange)
    }

    Box(
        modifier = modifier.height(40.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .onSizeChanged { sliderWidth = it.width.toFloat() }
                .pointerInput(valueRange) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            onValueChange(xToValue(offset.x))
                        },
                        onDrag = { change, _ ->
                            onValueChange(xToValue(change.position.x))
                        },
                        onDragEnd = {  },
                        onDragCancel = {  }
                    )
                }
        ) {
            val progress =
                (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)
            val thumbX =
                leftPaddingPx + (size.width - leftPaddingPx - rightPaddingPx) * progress
            val centerY = size.height / 2f

            drawLine(
                color = inactiveColor,
                start = Offset(leftPaddingPx, centerY),
                end = Offset(size.width - rightPaddingPx, centerY),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )

            if (progress > 0f) {
                drawLine(
                    color = activeColor,
                    start = Offset(leftPaddingPx, centerY),
                    end = Offset(thumbX.coerceAtMost(size.width - rightPaddingPx), centerY),
                    strokeWidth = 6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            drawCircle(
                color = Color.Black.copy(alpha = 0.1f),
                radius = 14.dp.toPx(),
                center = Offset(thumbX, centerY + 2.dp.toPx())
            )


            drawCircle(
                color = activeColor,
                radius = 12.dp.toPx(),
                center = Offset(thumbX, centerY)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.3f),
                radius = 6.dp.toPx(),
                center = Offset(thumbX, centerY)
            )
        }
    }
}