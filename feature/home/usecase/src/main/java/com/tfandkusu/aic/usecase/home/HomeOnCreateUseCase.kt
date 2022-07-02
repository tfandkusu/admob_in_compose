package com.tfandkusu.aic.usecase.home

import com.tfandkusu.aic.data.repository.GithubRepoRepository
import com.tfandkusu.aic.data.repository.NumberOfStartsRepository
import com.tfandkusu.aic.model.GithubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeOnCreateUseCase {
    fun execute(): Flow<List<GithubRepo>>
}

class HomeOnCreateUseCaseImpl @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    private val numberOfStartsRepository: NumberOfStartsRepository
) : HomeOnCreateUseCase {
    override fun execute(): Flow<List<GithubRepo>> {
        numberOfStartsRepository.countUp()
        return githubRepoRepository.listAsFlow()
    }
}
