package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.data.repository.NumberOfStartsRepository
import com.tfandkusu.template.model.GithubRepo
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
