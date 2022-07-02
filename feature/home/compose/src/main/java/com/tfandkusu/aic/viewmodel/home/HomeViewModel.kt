package com.tfandkusu.aic.viewmodel.home

import androidx.compose.runtime.Stable
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.viewmodel.UnidirectionalViewModel
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class Favorite(val id: Long, val on: Boolean) : HomeEvent()
}

sealed class HomeEffect

@Stable
data class HomeStateItem(
    val repo: GithubRepo
)

data class HomeState(
    val progress: Boolean = true,
    val items: List<HomeStateItem> = listOf()
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
