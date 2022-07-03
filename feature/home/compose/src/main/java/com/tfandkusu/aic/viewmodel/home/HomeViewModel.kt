package com.tfandkusu.aic.viewmodel.home

import androidx.compose.runtime.Stable
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.viewmodel.UnidirectionalViewModel
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class Favorite(val id: Long, val on: Boolean) : HomeEvent()

    data class OnUpdateVisiblePosition(val visiblePosition: HomeVisiblePosition) : HomeEvent()
}

sealed class HomeEffect

sealed class HomeStateItem {
    @Stable
    data class HomeStateRepoItem(
        val repo: GithubRepo
    ) : HomeStateItem()

    @Stable
    data class HomeStateAdItem(
        val id: Long,
        val visible: Boolean
    ) : HomeStateItem()
}

data class HomeVisiblePosition(
    val firstIndex: Int,
    val lastIndex: Int
)

data class HomeState(
    val progress: Boolean = true,
    val items: List<HomeStateItem> = listOf()
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
