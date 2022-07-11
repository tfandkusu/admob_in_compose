package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.tfandkusu.aic.home.compose.databinding.ListItemNativeAdBinding

class NativeAdViewRecycler : ViewRecycler<NativeAdView, NativeAd>() {
    override fun createView(context: Context, param: NativeAd): NativeAdView {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ListItemNativeAdBinding.inflate(inflater)
        binding.adView.mediaView = binding.adMedia
        binding.adView.headlineView = binding.adHeadline
        binding.adView.bodyView = binding.adBody
        binding.adView.advertiserView = binding.adAdvertiser
        (binding.adView.headlineView as TextView).text = param.headline
        param.mediaContent?.let {
            binding.adView.mediaView?.setMediaContent(it)
        }
        binding.adView.mediaView?.setImageScaleType(ImageView.ScaleType.FIT_CENTER)
        (binding.adView.bodyView as TextView).text = param.body
        (binding.adView.advertiserView as TextView).text = param.advertiser
        binding.adView.setNativeAd(param)
        return binding.root
    }
}
