package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

class AdViewRecycler {

    private val recyclableAdViewList = mutableListOf<AdView>()

    fun onLayoutChange(context: Context): AdView {
        return if (recyclableAdViewList.isNotEmpty()) {
            recyclableAdViewList.removeAt(0)
        } else {
            val unitId = context.getString(R.string.ad_mob_banner_unit_id)
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adView
        }
    }

    fun onDetached(adView: AdView) {
        (adView.parent as ViewGroup).removeView(adView)
        recyclableAdViewList.add(adView)
    }

    fun clear() {
        recyclableAdViewList.clear()
    }
}
