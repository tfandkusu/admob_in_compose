package com.tfandkusu.aic.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tfandkusu.aic.catalog.GitHubRepoCatalog
import com.tfandkusu.aic.error.NetworkErrorException
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.aic.usecase.home.HomeLoadUseCase
import com.tfandkusu.aic.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.aic.viewmodel.error.ApiErrorState
import com.tfandkusu.aic.viewmodel.mockStateObserver
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var loadUseCase: HomeLoadUseCase

    @MockK(relaxed = true)
    private lateinit var onCreateUseCase: HomeOnCreateUseCase

    @MockK(relaxed = true)
    private lateinit var favoriteUseCase: HomeFavoriteUseCase

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModelImpl(loadUseCase, onCreateUseCase, favoriteUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onCreate() = runTest {
        val repos = GitHubRepoCatalog.getList()
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        // Use usecase only once.
        viewModel.event(HomeEvent.OnCreate)
        verifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(
                HomeState(
                    items = repos.map {
                        HomeStateItem(it)
                    }
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadSuccess() = runTest {
        val stateMockObserver = viewModel.state.mockStateObserver()
        val errorStateMockObserver = viewModel.error.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            errorStateMockObserver.onChanged(ApiErrorState())
            errorStateMockObserver.onChanged(ApiErrorState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            stateMockObserver.onChanged(HomeState(progress = false))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadError() = runTest {
        coEvery {
            loadUseCase.execute()
        } throws NetworkErrorException()
        val stateMockObserver = viewModel.state.mockStateObserver()
        val errorMockStateObserver = viewModel.error.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            errorMockStateObserver.onChanged(ApiErrorState())
            errorMockStateObserver.onChanged(ApiErrorState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            errorMockStateObserver.onChanged(ApiErrorState(network = true))
            stateMockObserver.onChanged(HomeState(progress = false))
        }
    }

    @Test
    fun favorite() {
        val repo1 = mockk<GithubRepo> {
            every { id } returns 1L
        }
        val repo2 = mockk<GithubRepo> {
            every { id } returns 2L
        }
        val repo3 = mockk<GithubRepo> {
            every { id } returns 3L
        }
        val repos = listOf(repo1, repo2, repo3)
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val items = listOf(
            HomeStateItem(repo1),
            HomeStateItem(repo2),
            HomeStateItem(repo3)
        )
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Favorite(2L, true))
        viewModel.event(HomeEvent.Favorite(2L, false))
        coVerifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(HomeState(items = items))
            favoriteUseCase.execute(2L, true)
            favoriteUseCase.execute(2L, false)
        }
    }
}
