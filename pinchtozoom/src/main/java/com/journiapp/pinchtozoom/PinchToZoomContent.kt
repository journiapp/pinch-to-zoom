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

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.fastForEach

/**
 * Wrapper for a composable that allows to zoom and pan it.
 *
 * @param modifier Modifier to be applied to the root of the component.
 * @param key Key of content.
 * Important to have unique key for elements from lazy lists, pagers. Otherwise zooming will not work properly.
 * @param showOriginal If true, the original composable will still be shown in the background when zooming is started.
 * false by default
 * @param content Composable that will be zoomed and panned.
 */
@Composable
fun PinchToZoom(
    modifier: Modifier = Modifier,
    key: Any? = null,
    showOriginal: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    var currentSize by remember { mutableStateOf(IntSize.Zero) }

    val controller = LocalPinchZoomableController.current

    Box(
        modifier = modifier
            .onGloballyPositioned {
                // save a global position and size of the composable
                currentSize = it.size
                currentPosition = it.localToRoot(Offset.Zero)
            }
            .pointerInput(key) {
                awaitEachGesture {
                    while (true) {
                        val event = awaitPointerEvent()
                        val zoom = event.calculateZoom()
                        val rotation = event.calculateRotation()
                        val offset = event.calculatePan()

                        // Gesture is performing if it is either zooming, rotating or panning
                        val gestureIsPerforming =
                            (zoom != 1f || offset != Offset.Zero || rotation != 0f)
                        // Check is any of the pointers is pressed and if any of the gestures is performing
                        if (event.changes.all { it.pressed } && gestureIsPerforming && event.changes.size > 1) {

                            // If zooming is started, set current position to the center of the gesture
                            if (controller.isZooming.not()) {
                                controller.position = currentPosition + offset
                            }

                            controller.scale *= zoom
                            controller.rotation += rotation
                            controller.offset += offset
                            controller.composable = content
                            controller.isZooming = true
                            controller.size = currentSize

                            // Consume pitch to zoom event
                            event.changes.fastForEach { change ->
                                change.consume()
                            }
                        } else {
                            // Reset state to default when gesture is ended
                            controller.reset()
                        }
                    }
                }
            }
    ) {
        when (controller.isZooming && !showOriginal && controller.composable == content) {
            // If zooming is started, show a placeholder with the same size
            true -> Box(modifier = Modifier.size(currentSize.toDpSize()))
            false -> content()
        }
    }
}