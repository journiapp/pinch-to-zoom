package com.journiapp.pinchtozoom

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize

@Composable
internal fun IntSize.toDpSize() = with(LocalDensity.current) {
    DpSize(width.toDp(), height.toDp())
}