package com.tfandkusu.aic.usecase.home

import com.tfandkusu.aic.catalog.GitHubRepoCatalog
import com.tfandkusu.aic.data.repository.GithubRepoRepository
import com.tfandkusu.aic.data.repository.NumberOfStartsRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeOnCreateUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var numberOfStartsRepository: NumberOfStartsRepository

    @MockK(relaxed = true)
    private lateinit var githubRepoRepository: GithubRepoRepository

    private lateinit var useCase: HomeOnCreateUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = HomeOnCreateUseCaseImpl(githubRepoRepository, numberOfStartsRepository)
    }

    @Test
    fun execute() = runBlocking {
        val repos = GitHubRepoCatalog.getList()
        every {
            githubRepoRepository.listAsFlow()
        } returns flow {
            emit(repos)
        }
        useCase.execute().first() shouldBe repos
        verifySequence {
            numberOfStartsRepository.countUp()
            githubRepoRepository.listAsFlow()
        }
    }
}
