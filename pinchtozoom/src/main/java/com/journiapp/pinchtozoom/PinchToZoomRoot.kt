/*
 * Copyright 2023 Journi GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.journiapp.pinchtozoom

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Root composable to handle zooming and panning of [PinchToZoom]
 *
 * @param backgroundOverlayColor Color of the background overlay when zooming is started.
 * defaults to Black with 55% alpha
 * @param content Content of the root composable.
 */
@Composable
fun PinchToZoomRoot(
    backgroundOverlayColor: Color = defaultOverlayColor,
    content: @Composable () -> Unit
) {
    val controller = remember { PinchZoomableController() }

    CompositionLocalProvider(
        LocalPinchZoomableController provides controller
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()

            // Crossfade responsible for background overlay when zooming is started
            Crossfade(targetState = controller.isZooming, label = crossfadeLabel) {
                if (it) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = backgroundOverlayColor)
                    )
                }
            }
            if (controller.isZooming) {
                // Draw composable that is pinched
                Box(
                    modifier = Modifier
                        //Set size of copy of the composable to the size of the original composable
                        .size(controller.size.toDpSize())
                        .graphicsLayer {
                            //Offset is based on a global position of the original composable
                            val offset = controller.position + controller.offset

                            scaleX = controller.scale
                            scaleY = controller.scale
                            rotationZ = controller.rotation
                            translationX = offset.x
                            translationY = offset.y
                        }
                ) {
                    controller.composable?.invoke(this)
                }
            }
        }
    }
}

private const val crossfadeLabel = "pinchToZoomCrossfade"

private val defaultOverlayColor = Color.Black.copy(alpha = 0.55f)