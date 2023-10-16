package com.journiapp.pinchtozoom

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class PinchToZoomTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pinch_zoomed_and_cleared_after_pinch_finished() {
        // Build Basic Usage sample
        composeTestRule.setContent {
            PinchToZoomRoot {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PinchToZoom {
                        val painter = painterResource(id = R.mipmap.sym_def_app_icon)
                        Image(
                            painter = painter,
                            contentDescription = "image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
            }
        }
        // Save original bounds
        val node = composeTestRule.onNodeWithContentDescription("image")
        val boundsBefore = node.fetchSemanticsNode().boundsInRoot

        // Perform pinching
        node.performTouchInput {
            down(0, center + Offset(-30f, 0f))
            down(1, center + Offset(+30f, 0f))

            moveBy(0, Offset(-70f, 0f))
            moveBy(1, Offset(+70f, 0f))
        }

        // check bounds changed while pinching
        val nodePinching = composeTestRule.onAllNodesWithContentDescription("image").onLast()
        assert(nodePinching != node)

        val boundsAfter = nodePinching.fetchSemanticsNode().boundsInRoot
        assert(boundsAfter.width > boundsBefore.width && boundsAfter.height > boundsBefore.height)

        // Finish pinching
        node.performTouchInput {
            up(0)
            up(1)
        }

        // check bounds are back to normal after pinch finished
        val nodePinchFinish = composeTestRule.onAllNodesWithContentDescription("image").onLast()

        val boundsFinish = nodePinchFinish.fetchSemanticsNode().boundsInRoot
        assert(boundsFinish.width == boundsBefore.width && boundsFinish.height == boundsBefore.height)
    }
}