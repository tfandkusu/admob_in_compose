package com.tfandkusu.aic.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.aic.usecase.home.HomeLoadUseCase
import com.tfandkusu.aic.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper
import com.tfandkusu.aic.viewmodel.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val loadUseCase: HomeLoadUseCase,
    private val onCreateUseCase: HomeOnCreateUseCase,
    private val favoriteUseCase: HomeFavoriteUseCase
) : HomeViewModel, ViewModel() {

    private val _error = ApiErrorViewModelHelper()

    override val error: ApiErrorViewModelHelper
        get() = _error

    override fun createDefaultState() = HomeState()

    private val _state = MutableLiveData(createDefaultState())

    override val state: LiveData<HomeState> = _state

    private val effectChannel = createEffectChannel()

    override val effect: Flow<HomeEffect> = effectChannel.receiveAsFlow()

    private var loaded = false

    private val nativeAdSourcesStateFlow = MutableStateFlow(
        (0 until HOME_NATIVE_AD_COUNT).map {
            HomeStateNativeAdItemSource(adKind = it)
        }
    )

    private data class ReposAndNativeAdSources(
        val repos: List<GithubRepo>,
        val nativeAdSources: List<HomeStateNativeAdItemSource>
    )

    override fun event(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                HomeEvent.Load -> {
                    error.release()
                    _state.update {
                        copy(progress = true)
                    }
                    try {
                        loadUseCase.execute()
                    } catch (e: Throwable) {
                        _error.catch(e)
                    } finally {
                        _state.update {
                            copy(progress = false)
                        }
                    }
                }
                HomeEvent.OnCreate -> {
                    if (!loaded) {
                        loaded = true
                        combine(
                            onCreateUseCase.execute(),
                            nativeAdSourcesStateFlow
                        ) { repos, nativeAdSources ->
                            ReposAndNativeAdSources(repos, nativeAdSources)
                        }.collect { reposAndNativeAdSources ->
                            val repos = reposAndNativeAdSources.repos
                            val nativeAdItemSource = reposAndNativeAdSources.nativeAdSources
                            _state.update {
                                copy(
                                    items = repos.flatMapIndexed { index, repo ->
                                        if ((index - 2) % 7 == 0) {
                                            val adCount = nativeAdItemSource.size
                                            listOf(
                                                // Infeed Ad
                                                // HomeStateItem.HomeStateBannerAdItem(index.toLong)
                                                HomeStateItem.HomeStateNativeAdItem(
                                                    index.toLong(),
                                                    nativeAdItemSource[((index - 2) / 7) % adCount]
                                                ),
                                                // Content
                                                HomeStateItem.HomeStateRepoItem(
                                                    repo
                                                )
                                            )
                                        } else {
                                            listOf(
                                                // Content
                                                HomeStateItem.HomeStateRepoItem(
                                                    repo
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                is HomeEvent.Favorite -> {
                    favoriteUseCase.execute(event.id, event.on)
                }
                is HomeEvent.OnLoadNativeAd -> {
                    val nativeAdSources = nativeAdSourcesStateFlow.value
                    val loadingIndex = nativeAdSources.indexOfFirst {
                        it.status == HomeStateNativeAdItemSourceStatus.LOADING
                    }
                    nativeAdSourcesStateFlow.value = nativeAdSources.mapIndexed { index, src ->
                        if (index == loadingIndex) {
                            event.nativeAd?.let {
                                src.copy(
                                    nativeAd = it,
                                    status = HomeStateNativeAdItemSourceStatus.SUCCESS,
                                    adKind = index
                                )
                            } ?: run {
                                src.copy(
                                    status = HomeStateNativeAdItemSourceStatus.FAILED,
                                    adKind = index
                                )
                            }
                        } else {
                            src
                        }
                    }
                }
            }
        }
    }
}
