package com.tfandkusu.aic.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.aic.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.aic.usecase.home.HomeLoadUseCase
import com.tfandkusu.aic.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper
import com.tfandkusu.aic.viewmodel.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
                        onCreateUseCase.execute().collect { repos ->
                            _state.update {
                                copy(
                                    items = repos.map {
                                        HomeStateItem(
                                            it
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                is HomeEvent.Favorite -> {
                    favoriteUseCase.execute(event.id, event.on)
                }
            }
        }
    }
}
