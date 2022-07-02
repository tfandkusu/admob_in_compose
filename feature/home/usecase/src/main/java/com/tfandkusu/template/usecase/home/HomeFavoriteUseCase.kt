package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.GithubRepoRepository
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
