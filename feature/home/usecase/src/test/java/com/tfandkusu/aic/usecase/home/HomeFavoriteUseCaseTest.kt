package com.tfandkusu.aic.usecase.home

import com.tfandkusu.aic.data.repository.GithubRepoRepository
import io.kotest.common.runBlocking
import io.mockk.MockKAnnotations
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class HomeFavoriteUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var repository: GithubRepoRepository

    private lateinit var useCase: HomeFavoriteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = HomeFavoriteUseCaseImpl(repository)
    }

    @Test
    fun execute() = runBlocking {
        useCase.execute(1L, true)
        coVerifySequence {
            repository.favorite(1L, true)
        }
    }
}
