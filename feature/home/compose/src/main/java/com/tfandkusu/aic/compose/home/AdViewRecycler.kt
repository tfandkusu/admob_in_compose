package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

class AdViewRecycler {

    private val adViewQueue = mutableListOf<AdView>()

    fun createOrDequeue(context: Context): AdView {
        return if (adViewQueue.isEmpty()) {
            val unitId = context.getString(R.string.ad_mob_banner_unit_id)
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adView
        } else {
            adViewQueue.removeAt(0)
        }
    }

    fun enqueue(adView: AdView) {
        (adView.parent as ViewGroup).removeView(adView)
        adViewQueue.add(adView)
    }

    fun clear() {
        adViewQueue.clear()
    }
}
