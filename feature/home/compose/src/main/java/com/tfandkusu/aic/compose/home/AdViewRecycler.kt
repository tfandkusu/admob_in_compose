package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R
import timber.log.Timber

class AdViewRecycler {
    private val androidViewMap = mutableMapOf<Int, AndroidView>()

    private data class AndroidView(
        val adView: AdView,
        val count: Int
    )

    private val recyclableAdViewList = mutableListOf<AdView>()

    fun onFactory(context: Context, index: Int): AdView {
        val androidView = androidViewMap[index]
        if (androidView != null) {
            val adView = androidView.adView
            Timber.d("onFactory $index fromMap")
            androidViewMap[index] = androidView.copy(count = androidView.count + 1)
            (adView.parent as ViewGroup).removeView(adView)
            return adView
        }
        if (recyclableAdViewList.isNotEmpty()) {
            Timber.d("onFactory $index fromList")
            val adView = recyclableAdViewList.removeAt(0)
            androidViewMap[index] = AndroidView(adView, 1)
            return adView
        }
        Timber.d("onFactory $index create")
        val unitId = context.getString(R.string.ad_mob_banner_unit_id)
        val adView = AdView(context)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = unitId
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        androidViewMap[index] = AndroidView(adView, 1)
        return adView
    }

    fun onDetached(index: Int) {
        val androidView = androidViewMap[index]
        if (androidView != null) {
            Timber.d("onDetached $index androidView.count = " + androidView.count)
            if (androidView.count >= 2) {
                androidViewMap[index] = androidView.copy(count = androidView.count - 1)
            } else {
                val adView = androidView.adView
                (adView.parent as ViewGroup).removeView(adView)
                recyclableAdViewList.add(adView)
                androidViewMap.remove(index)
            }
        }
    }

    fun clear() {
        androidViewMap.clear()
        recyclableAdViewList.clear()
    }
}
