package com.tfandkusu.template.usecase.info

import com.tfandkusu.template.data.repository.NumberOfStartsRepository
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
