package com.tfandkusu.aic.viewmodel.home

import androidx.compose.runtime.Stable
import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.viewmodel.UnidirectionalViewModel
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper

const val NATIVE_AD_COUNT = 3

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class Favorite(val id: Long, val on: Boolean) : HomeEvent()

    data class OnLoadNativeAd(val nativeAd: NativeAd?) : HomeEvent()
}

sealed class HomeEffect

enum class HomeStateNativeAdItemSourceStatus {
    LOADING,
    SUCCESS,
    FAILED
}

data class HomeStateNativeAdItemSource(
    val status: HomeStateNativeAdItemSourceStatus = HomeStateNativeAdItemSourceStatus.LOADING,
    val nativeAd: NativeAd? = null
)

sealed class HomeStateItem {
    @Stable
    data class HomeStateRepoItem(
        val repo: GithubRepo
    ) : HomeStateItem()

    @Stable
    data class HomeStateBannerAdItem(
        val id: Long
    ) : HomeStateItem()

    @Stable
    data class HomeStateNativeAdItem(
        val id: Long,
        val source: HomeStateNativeAdItemSource
    ) : HomeStateItem()
}

data class HomeState(
    val progress: Boolean = true,
    val items: List<HomeStateItem> = listOf()
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
