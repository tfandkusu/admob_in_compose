package com.tfandkusu.aic.compose.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

@Composable
fun AdMobBannerAndroidView() {
    val unitId = stringResource(R.string.ad_mob_banner_unit_id)
    AndroidView(
        modifier = Modifier
            .width(320.dp)
            .height(50.dp),
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adView
        }
    )
}
