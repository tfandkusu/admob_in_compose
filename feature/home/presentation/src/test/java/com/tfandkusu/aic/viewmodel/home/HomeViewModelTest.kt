package com.tfandkusu.aic.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
        val repos = (0 until 10).map {
            mockk<GithubRepo> {
                every { id } returns (it + 1).toLong()
            }
        }
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val items = listOf(
            HomeStateItem.HomeStateRepoItem(repos[0]),
            HomeStateItem.HomeStateRepoItem(repos[1]),
            HomeStateItem.HomeStateAdItem(2),
            HomeStateItem.HomeStateRepoItem(repos[2]),
            HomeStateItem.HomeStateRepoItem(repos[3]),
            HomeStateItem.HomeStateRepoItem(repos[4]),
            HomeStateItem.HomeStateRepoItem(repos[5]),
            HomeStateItem.HomeStateRepoItem(repos[6]),
            HomeStateItem.HomeStateRepoItem(repos[7]),
            HomeStateItem.HomeStateRepoItem(repos[8]),
            HomeStateItem.HomeStateAdItem(9),
            HomeStateItem.HomeStateRepoItem(repos[9])
        )
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        // Use usecase only once.
        viewModel.event(HomeEvent.OnCreate)
        verifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(
                HomeState(items = items)
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
        val repos = (0 until 3).map {
            mockk<GithubRepo> {
                every { id } returns (it + 1).toLong()
            }
        }
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val items = listOf(
            HomeStateItem.HomeStateRepoItem(repos[0]),
            HomeStateItem.HomeStateRepoItem(repos[1]),
            HomeStateItem.HomeStateAdItem(2),
            HomeStateItem.HomeStateRepoItem(repos[2])
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
