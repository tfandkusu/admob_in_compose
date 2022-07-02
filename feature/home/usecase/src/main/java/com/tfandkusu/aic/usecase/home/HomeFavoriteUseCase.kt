package com.tfandkusu.aic.usecase.home

import com.tfandkusu.aic.data.repository.GithubRepoRepository
import javax.inject.Inject

interface HomeFavoriteUseCase {
    suspend fun execute(id: Long, on: Boolean)
}

class HomeFavoriteUseCaseImpl @Inject constructor(
    private val repository: GithubRepoRepository
) : HomeFavoriteUseCase {
    override suspend fun execute(id: Long, on: Boolean) {
        repository.favorite(id, on)
    }
}
