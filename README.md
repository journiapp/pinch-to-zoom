# Jetpack Compose Pinch-To-Zoom Library

The Jetpack Compose Pinch-To-Zoom library is a tool that provides pinch-to-zoom functionality for Compose Views. It consists of two Composables:

1. **PinchToZoomRoot**: This Composable should be used as a root wrapper / receiver for zooming and panning of a Composable views.

2. **PinchToZoom**: This Composable should be used as a direct wrapper for the view that needs to be zoomed and panned.

## Usage

1. Import the library into your project.

Add Jitpack to your repositories, in your top-level `build.gradle` or `settings.gradle` file:

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```
Then add the library to your dependencies in your app-level `build.gradle` file:

```groovy
dependencies {
    ...
    implementation 'com.github.journiapp:pinch-to-zoom:version'
}
```

2. Wrap your zoomable content with `PinchToZoom` and your root view with `PinchToZoomRoot`.

3. Customize the behavior by adjusting the provided parameters as needed.

```kotlin
@Composable
fun MyZoomableScreen() {
    PinchToZoomRoot {
        // Your UI layout
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PinchToZoom {
                // Zoomable content
                Image(
                    painter = painterResource(id = R.drawable.my_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
```

Check for more samples in the [sample app](https://github.com/journiapp/pinch-to-zoom/tree/main/app)

## PinchToZoomRoot

The `PinchToZoomRoot` Composable allows you to handle zooming and panning of a child Composable. Here is how you can use it:

```kotlin
@Composable
fun MyScreen() {
    PinchToZoomRoot {
        // Your content of a screen goes here
    }
}
```

### Parameters

- `backgroundOverlayColor`: Color of the background overlay when zooming is started. Defaults to Black with 55% alpha.
- `content`: The content of the root Composable.

## PinchToZoom

The `PinchToZoom` Composable allows you to wrap a Composable view that needs to be zoomed and panned. Here is how you can use it:

```kotlin
@Composable
fun MyZoomableContent() {
    PinchToZoom {
        // Your zoomable content goes here
    }
}
```

### Parameters

- `modifier`: Modifier to be applied to the root of the component.
- `key`: Key of the content. It's important to have a unique key for elements from lazy lists, pagers, etc., to ensure proper zooming behavior.
- `showOriginal`: If `true`, the original Composable will still be shown in the background when zooming is started (default is `false`).
- `content`: The Composable that will be zoomed and panned.

## License

Copyright 2023 Journi GmbH

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

---

Feel free to contribute to this open-source library and make it even better! If you encounter any issues or have suggestions, please open an issue or submit a pull request. Thank you for using the Jetpack Compose Pinch-To-Zoom library!
