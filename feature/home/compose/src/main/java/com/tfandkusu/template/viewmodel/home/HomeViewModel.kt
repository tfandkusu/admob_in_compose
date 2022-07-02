package com.tfandkusu.template.viewmodel.home

import androidx.compose.runtime.Stable
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.viewmodel.UnidirectionalViewModel
import com.tfandkusu.template.viewmodel.error.ApiErrorViewModelHelper

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
