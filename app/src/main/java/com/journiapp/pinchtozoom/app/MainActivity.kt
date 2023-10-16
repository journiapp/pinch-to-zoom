package com.journiapp.pinchtozoom.app

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.journiapp.pinchtozoom.PinchToZoom
import com.journiapp.pinchtozoom.PinchToZoomRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinchToZoomScreen()
        }
    }
}

@Composable
private fun PinchToZoomScreen() {
    PinchToZoomRoot {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(100) { index ->
                    PinchItem(index)
                }
            }
        )
    }
}

@Composable
private fun PinchItem(index: Int) {
    // key is important for lazy subcomposition to work properly
    PinchToZoom(key = index) {
        val painter = painterResource(id = R.mipmap.sym_def_app_icon)
        Image(
            modifier = Modifier
                .size(150.dp)
                .padding(10.dp)
                .align(Alignment.Center),
            painter = painter,
            contentDescription = "image $index",
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$index"
        )
    }
}