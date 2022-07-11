package com.tfandkusu.aic.compose.home

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

class AdViewRecycler : ViewRecycler<AdView, Unit>() {
    override fun createView(context: Context, param: Unit): AdView {
        val unitId = context.getString(R.string.ad_mob_banner_unit_id)
        val adView = AdView(context)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = unitId
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        return adView
    }
}
