package com.journiapp.pinchtozoom

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

internal class PinchZoomableController {
    var scale: Float by mutableFloatStateOf(1f)
    var rotation: Float by  mutableFloatStateOf(1f)
    var offset: Offset by  mutableStateOf(Offset.Zero)
    var size: IntSize by  mutableStateOf(IntSize.Zero)
    var position: Offset by  mutableStateOf(Offset.Zero)
    var isZooming: Boolean by  mutableStateOf(false)
    var composable: (@Composable BoxScope.() -> Unit)? by  mutableStateOf(null)

    fun reset() {
        scale = 1f
        rotation = 0f
        offset = Offset.Zero
        size = IntSize.Zero
        position = Offset.Zero
        isZooming = false
        composable = null
    }
}

internal val LocalPinchZoomableController = compositionLocalOf {
    PinchZoomableController()
}