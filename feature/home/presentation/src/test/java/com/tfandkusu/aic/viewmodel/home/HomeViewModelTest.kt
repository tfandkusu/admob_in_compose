package com.tfandkusu.aic.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.ads.nativead.NativeAd
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
    fun onCreateBannerAd() = runTest {
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
            HomeStateItem.HomeStateBannerAdItem(2),
            HomeStateItem.HomeStateRepoItem(repos[2]),
            HomeStateItem.HomeStateRepoItem(repos[3]),
            HomeStateItem.HomeStateRepoItem(repos[4]),
            HomeStateItem.HomeStateRepoItem(repos[5]),
            HomeStateItem.HomeStateRepoItem(repos[6]),
            HomeStateItem.HomeStateRepoItem(repos[7]),
            HomeStateItem.HomeStateRepoItem(repos[8]),
            HomeStateItem.HomeStateBannerAdItem(9),
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

    fun favoriteBannerAd() {
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
            HomeStateItem.HomeStateBannerAdItem(2),
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

    @ExperimentalCoroutinesApi
    @Test
    fun onCreateNativeAd() = runTest {
        // On load native ad
        val nativeAd = mockk<NativeAd>()
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
        val items1 = listOf(
            HomeStateItem.HomeStateRepoItem(repos[0]),
            HomeStateItem.HomeStateRepoItem(repos[1]),
            HomeStateItem.HomeStateNativeAdItem(2, HomeStateNativeAdItemSource(adKind = 0)),
            HomeStateItem.HomeStateRepoItem(repos[2]),
            HomeStateItem.HomeStateRepoItem(repos[3]),
            HomeStateItem.HomeStateRepoItem(repos[4]),
            HomeStateItem.HomeStateRepoItem(repos[5]),
            HomeStateItem.HomeStateRepoItem(repos[6]),
            HomeStateItem.HomeStateRepoItem(repos[7]),
            HomeStateItem.HomeStateRepoItem(repos[8]),
            HomeStateItem.HomeStateNativeAdItem(9, HomeStateNativeAdItemSource(adKind = 1)),
            HomeStateItem.HomeStateRepoItem(repos[9])
        )
        val items2 = listOf(
            HomeStateItem.HomeStateRepoItem(repos[0]),
            HomeStateItem.HomeStateRepoItem(repos[1]),
            HomeStateItem.HomeStateNativeAdItem(
                2,
                HomeStateNativeAdItemSource(HomeStateNativeAdItemSourceStatus.SUCCESS, 0, nativeAd)
            ),
            HomeStateItem.HomeStateRepoItem(repos[2]),
            HomeStateItem.HomeStateRepoItem(repos[3]),
            HomeStateItem.HomeStateRepoItem(repos[4]),
            HomeStateItem.HomeStateRepoItem(repos[5]),
            HomeStateItem.HomeStateRepoItem(repos[6]),
            HomeStateItem.HomeStateRepoItem(repos[7]),
            HomeStateItem.HomeStateRepoItem(repos[8]),
            HomeStateItem.HomeStateNativeAdItem(9, HomeStateNativeAdItemSource(adKind = 1)),
            HomeStateItem.HomeStateRepoItem(repos[9])
        )
        val items3 = listOf(
            HomeStateItem.HomeStateRepoItem(repos[0]),
            HomeStateItem.HomeStateRepoItem(repos[1]),
            HomeStateItem.HomeStateNativeAdItem(
                2,
                HomeStateNativeAdItemSource(HomeStateNativeAdItemSourceStatus.SUCCESS, 0, nativeAd)
            ),
            HomeStateItem.HomeStateRepoItem(repos[2]),
            HomeStateItem.HomeStateRepoItem(repos[3]),
            HomeStateItem.HomeStateRepoItem(repos[4]),
            HomeStateItem.HomeStateRepoItem(repos[5]),
            HomeStateItem.HomeStateRepoItem(repos[6]),
            HomeStateItem.HomeStateRepoItem(repos[7]),
            HomeStateItem.HomeStateRepoItem(repos[8]),
            HomeStateItem.HomeStateNativeAdItem(
                9,
                HomeStateNativeAdItemSource(HomeStateNativeAdItemSourceStatus.FAILED, 1, null)
            ),
            HomeStateItem.HomeStateRepoItem(repos[9])
        )
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        // Use usecase only once.
        viewModel.event(HomeEvent.OnCreate)
        // On load native ad
        viewModel.event(HomeEvent.OnLoadNativeAd(nativeAd))
        // Loading native ad is failed
        viewModel.event(HomeEvent.OnLoadNativeAd(null))
        verifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(
                HomeState(items = items1)
            )
            mockStateObserver.onChanged(
                HomeState(items = items2)
            )
            mockStateObserver.onChanged(
                HomeState(items = items3)
            )
        }
    }

    @Test
    fun favoriteNativeAd() {
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
            HomeStateItem.HomeStateNativeAdItem(2, HomeStateNativeAdItemSource()),
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
}
