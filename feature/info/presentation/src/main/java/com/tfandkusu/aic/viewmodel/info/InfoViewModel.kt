package com.tfandkusu.aic.viewmodel.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.aic.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.aic.viewmodel.UnidirectionalViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InfoEvent {
    object OnClickAbout : InfoEvent()
}

sealed class InfoEffect {
    data class ShowAbout(val numberOfStarts: Int) : InfoEffect()
}

object InfoState

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val onClickAboutUseCase: InfoOnClickAboutUseCase
) : UnidirectionalViewModel<InfoEvent, InfoEffect, InfoState>,
    ViewModel() {

    override fun createDefaultState() = InfoState

    private val _state = MutableLiveData(createDefaultState())

    override val state: LiveData<InfoState>
        get() = _state

    private val effectChannel = createEffectChannel()

    override val effect = effectChannel.receiveAsFlow()

    override fun event(event: InfoEvent) {
        viewModelScope.launch {
            when (event) {
                InfoEvent.OnClickAbout -> {
                    val result = onClickAboutUseCase.execute()
                    effectChannel.send(InfoEffect.ShowAbout(result.numberOfStarts))
                }
            }
        }
    }
}
