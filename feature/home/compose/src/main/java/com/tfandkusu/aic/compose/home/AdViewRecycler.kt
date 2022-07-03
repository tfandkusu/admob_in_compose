package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.aic.home.compose.R

class AdViewRecycler {
    private val adViewMap = mutableMapOf<Int, AdView>()

    private val recyclableAdViewList = mutableListOf<AdView>()

    fun create(context: Context, index: Int): AdView {
        return if (recyclableAdViewList.isEmpty()) {
            val unitId = context.getString(R.string.ad_mob_banner_unit_id)
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adViewMap[index] = adView
            adView
        } else {
            val adView = recyclableAdViewList.removeAt(0)
            adViewMap[index] = adView
            adView
        }
    }

    fun recycle(firstVisibleIndex: Int, lastVisibleIndex: Int) {
        val outIndexList = adViewMap.keys.filter { index ->
            index < firstVisibleIndex || index > lastVisibleIndex
        }
        outIndexList.mapNotNull { index ->
            val adView = adViewMap[index]
            adViewMap.remove(index)
            adView
        }.map { adView ->
            adView.parent?.let { parent ->
                if (parent is ViewGroup) {
                    parent.removeView(adView)
                }
                recyclableAdViewList.add(adView)
            }
        }
    }

    fun clear() {
        adViewMap.clear()
        recyclableAdViewList.clear()
    }
}
