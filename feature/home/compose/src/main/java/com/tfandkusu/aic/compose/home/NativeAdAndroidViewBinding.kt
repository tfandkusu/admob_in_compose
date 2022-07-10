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
            val binding = ListItemNativeAdBinding.inflate(inflater, parent, attachToParent)
            binding.adView.mediaView = binding.adMedia
            binding.adView.headlineView = binding.adHeadline
            binding.adView.bodyView = binding.adBody
            binding.adView.advertiserView = binding.adAdvertiser
            (binding.adView.headlineView as TextView).text = nativeAd.headline
            nativeAd.mediaContent?.let {
                binding.adView.mediaView?.setMediaContent(it)
            }
            binding.adView.mediaView?.setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            (binding.adView.bodyView as TextView).text = nativeAd.body
            (binding.adView.advertiserView as TextView).text = nativeAd.advertiser
            binding.adView.setNativeAd(nativeAd)
            binding
        }
    )
}
