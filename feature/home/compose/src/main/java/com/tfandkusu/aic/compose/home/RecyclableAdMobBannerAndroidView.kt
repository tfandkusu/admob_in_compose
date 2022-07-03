package com.tfandkusu.aic.compose.home

import android.widget.FrameLayout
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RecyclableAdMobBannerAndroidView(adViewRecycler: AdViewRecycler, index: Int) {
    AndroidView(
        modifier = Modifier
            .width(320.dp)
            .height(50.dp),
        factory = { context ->
            val frameLayout = FrameLayout(context)
            val adView = adViewRecycler.create(context, index)
            frameLayout.addView(adView)
            frameLayout
        }
    )
}
