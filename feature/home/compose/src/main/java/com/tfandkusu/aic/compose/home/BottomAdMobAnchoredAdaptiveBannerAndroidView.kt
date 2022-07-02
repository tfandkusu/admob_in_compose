package com.tfandkusu.aic.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

@Composable
fun BottomAdMobAnchoredAdaptiveBannerAndroidView() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((adSize.height + 2).dp)
            .background(color = colorResource(R.color.ad_background))
            .padding(top = 1.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val unitId = stringResource(R.string.ad_mob_banner_unit_id)
        AndroidView(
            factory = { context ->
                val adView = AdView(context)
                adView.setAdSize(adSize)
                adView.adUnitId = unitId
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)
                adView
            }
        )
    }
}
