package com.tfandkusu.aic.compose.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R
import com.tfandkusu.aic.home.compose.databinding.AdViewFrameBinding

@Composable
fun RecyclableAdMobBannerAndroidViewBinding() {
    val unitId = stringResource(R.string.ad_mob_banner_unit_id)
    AndroidViewBinding(
        modifier = Modifier
            .width(320.dp)
            .height(50.dp),
        factory = AdViewFrameBinding::inflate
    ) {
        if (root.childCount == 0) {
            val adView = AdView(root.context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            root.addView(adView)
        }
    }
}
