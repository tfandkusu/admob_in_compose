package com.tfandkusu.aic.viewmodel.home

import com.google.android.gms.ads.nativead.NativeAd
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import org.junit.Test

class HomeStateNativeAdItemSourceTest {

    @Test
    fun testEqualsTrue() {
        val nativeAd = mockk<NativeAd>()
        val a = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.SUCCESS,
            0,
            nativeAd
        )
        val b = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.SUCCESS,
            0,
            nativeAd
        )
        a shouldBe b
    }

    @Test
    fun testEqualsFalse() {
        val nativeAd = mockk<NativeAd>()
        val a = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.SUCCESS,
            0,
            nativeAd
        )
        val b = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.SUCCESS,
            1,
            nativeAd
        )
        a shouldNotBe b
        val c = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.SUCCESS,
            0,
            nativeAd
        )
        val d = HomeStateNativeAdItemSource(
            HomeStateNativeAdItemSourceStatus.FAILED,
            0,
            null
        )
        c shouldNotBe d
    }
}
