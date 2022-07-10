package com.tfandkusu.aic.viewmodel.home

import androidx.compose.runtime.Stable
import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.viewmodel.UnidirectionalViewModel
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper

const val HOME_NATIVE_AD_COUNT = 3

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

@Stable
data class HomeStateNativeAdItemSource(
    val status: HomeStateNativeAdItemSourceStatus = HomeStateNativeAdItemSourceStatus.LOADING,
    val adKind: Int = 0,
    val nativeAd: NativeAd? = null
) {
    override fun equals(other: Any?): Boolean {
        return if (other is HomeStateNativeAdItemSource) {
            status == other.status && adKind == other.adKind
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + adKind
        return result
    }
}

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
