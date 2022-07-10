package com.tfandkusu.aic.compose.home

import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.aic.home.compose.databinding.ListItemNativeAdBinding

@Composable
fun NativeAdAndroidViewBinding(nativeAd: NativeAd) {
    AndroidViewBinding(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        factory = { inflater, parent, attachToParent ->
            ListItemNativeAdBinding.inflate(inflater, parent, attachToParent)
        },
        update = {
            adView.mediaView = this.adMedia
            adView.headlineView = this.adHeadline
            adView.bodyView = this.adBody
            adView.advertiserView = this.adAdvertiser
            (adView.headlineView as TextView).text = nativeAd.headline
            nativeAd.mediaContent?.let {
                adView.mediaView?.setMediaContent(it)
            }
            adView.mediaView?.setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            (adView.bodyView as TextView).text = nativeAd.body
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.setNativeAd(nativeAd)
        }
    )
}
