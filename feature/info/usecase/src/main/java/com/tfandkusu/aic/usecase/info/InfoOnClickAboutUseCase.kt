package com.tfandkusu.aic.usecase.info

import com.tfandkusu.aic.data.repository.NumberOfStartsRepository
import javax.inject.Inject

data class InfoOnClickAboutUseCaseResult(val numberOfStarts: Int)

interface InfoOnClickAboutUseCase {
    fun execute(): InfoOnClickAboutUseCaseResult
}

class InfoOnClickAboutUseCaseImpl @Inject constructor(
    private val repository: NumberOfStartsRepository
) : InfoOnClickAboutUseCase {
    override fun execute(): InfoOnClickAboutUseCaseResult {
        return InfoOnClickAboutUseCaseResult(repository.get())
    }
}
