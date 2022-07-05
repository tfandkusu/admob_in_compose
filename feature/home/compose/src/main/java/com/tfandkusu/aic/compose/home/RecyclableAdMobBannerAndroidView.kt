package com.tfandkusu.aic.compose.home

import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdView

@Composable
fun RecyclableAdMobBannerAndroidView(adViewRecycler: AdViewRecycler) {
    AndroidView(
        modifier = Modifier
            .width(320.dp)
            .height(50.dp),
        factory = { context ->
            val frameLayout = FrameLayout(context)
            frameLayout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if (frameLayout.childCount == 0) {
                    frameLayout.post {
                        val adView = adViewRecycler.createOrDequeue(context)
                        frameLayout.addView(adView)
                    }
                }
            }
            frameLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                }

                override fun onViewDetachedFromWindow(view: View) {
                    if (frameLayout.childCount >= 1) {
                        val adView = frameLayout.getChildAt(0) as AdView
                        adViewRecycler.enqueue(adView)
                    }
                }
            })
            frameLayout
        }
    )
}
