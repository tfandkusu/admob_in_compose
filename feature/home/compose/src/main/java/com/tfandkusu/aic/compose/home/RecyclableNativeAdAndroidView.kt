package com.tfandkusu.aic.compose.home

import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun RecyclableNativeAdAndroidView(
    nativeAdViewRecycler: NativeAdViewRecycler,
    nativeAd: NativeAd
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        factory = { context ->
            val frameLayout = FrameLayout(context)
            frameLayout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if (frameLayout.childCount == 0) {
                    frameLayout.post {
                        val adView = nativeAdViewRecycler.createOrDequeue(context, nativeAd)
                        frameLayout.addView(adView)
                    }
                }
            }
            frameLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                }

                override fun onViewDetachedFromWindow(view: View) {
                    if (frameLayout.childCount >= 1) {
                        val nativeAdView = frameLayout.getChildAt(0) as NativeAdView
                        nativeAdViewRecycler.enqueue(nativeAdView)
                    }
                }
            })
            frameLayout
        }
    )
}
