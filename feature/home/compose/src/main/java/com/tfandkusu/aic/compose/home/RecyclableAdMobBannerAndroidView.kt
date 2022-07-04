package com.tfandkusu.aic.compose.home

import android.view.View
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
            val adView = adViewRecycler.onFactory(context, index)
            frameLayout.addView(adView)
            frameLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                }

                override fun onViewDetachedFromWindow(view: View) {
                    adViewRecycler.onDetached(index)
                }
            })
            frameLayout
        }
    )
}
