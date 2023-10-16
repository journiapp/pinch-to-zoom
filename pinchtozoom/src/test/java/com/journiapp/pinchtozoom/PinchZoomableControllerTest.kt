package com.journiapp.pinchtozoom

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class PinchZoomableControllerTest {

    @Test
    fun `initialized with default values`() = runTest {
        val controller = PinchZoomableController()

        assert(controller.scale == 1f)
        assert(controller.rotation == 0f)
        assert(controller.offset == Offset.Zero)
        assert(controller.size == IntSize.Zero)
        assert(controller.position == Offset.Zero)
        assert(!controller.isZooming)
        assert(controller.composable == null)
    }

    @Test
    fun `reset to default values after reset() is called`() = runTest {
        val controller = PinchZoomableController()
        controller.scale = 2f
        controller.rotation = 90f
        controller.offset = Offset(10f, 10f)
        controller.size = IntSize(100, 100)
        controller.position = Offset(10f, 10f)
        controller.isZooming = true
        controller.composable = {}

        controller.reset()

        assert(controller.scale == 1f)
        assert(controller.rotation == 0f)
        assert(controller.offset == Offset.Zero)
        assert(controller.size == IntSize.Zero)
        assert(controller.position == Offset.Zero)
        assert(!controller.isZooming)
        assert(controller.composable == null)
    }
}