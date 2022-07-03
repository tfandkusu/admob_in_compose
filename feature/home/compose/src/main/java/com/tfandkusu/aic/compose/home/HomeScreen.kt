package com.tfandkusu.aic.compose.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.aic.catalog.GitHubRepoCatalog
import com.tfandkusu.aic.compose.MyTopAppBar
import com.tfandkusu.aic.compose.home.listitem.AdListItem
import com.tfandkusu.aic.compose.home.listitem.GitHubRepoListItem
import com.tfandkusu.aic.home.compose.R
import com.tfandkusu.aic.ui.theme.MyAppTheme
import com.tfandkusu.aic.view.error.ApiError
import com.tfandkusu.aic.view.info.InfoActivityAlias
import com.tfandkusu.aic.viewmodel.error.ApiErrorViewModelHelper
import com.tfandkusu.aic.viewmodel.error.useErrorState
import com.tfandkusu.aic.viewmodel.home.HomeEffect
import com.tfandkusu.aic.viewmodel.home.HomeEvent
import com.tfandkusu.aic.viewmodel.home.HomeState
import com.tfandkusu.aic.viewmodel.home.HomeStateItem
import com.tfandkusu.aic.viewmodel.home.HomeViewModel
import com.tfandkusu.aic.viewmodel.use
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeListKey(
    val contentType: Int,
    val id: Long
) : Parcelable

private const val CONTENT_TYPE_REPO = 1

private const val CONTENT_TYPE_AD = 2

@Composable
fun HomeScreen(viewModel: HomeViewModel, isPreview: Boolean = false) {
    val context = LocalContext.current
    val (state, _, dispatch) = use(viewModel)
    LaunchedEffect(Unit) {
        dispatch(HomeEvent.OnCreate)
        dispatch(HomeEvent.Load)
    }
    val errorState = useErrorState(viewModel.error)
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, InfoActivityAlias::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.action_information)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (errorState.noError()) {
                if (state.progress) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        state.items.map {
                            when (it) {
                                is HomeStateItem.HomeStateAdItem -> {
                                    item(key = HomeListKey(CONTENT_TYPE_AD, it.id)) {
                                        AdListItem(isPreview = isPreview)
                                    }
                                }
                                is HomeStateItem.HomeStateRepoItem -> {
                                    item(key = HomeListKey(CONTENT_TYPE_REPO, it.repo.id)) {
                                        GitHubRepoListItem(it) { id, on ->
                                            dispatch(HomeEvent.Favorite(id, on))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ApiError(
                    errorState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    viewModel.event(HomeEvent.Load)
                }
            }
            if (isPreview) {
                DummyBottomAdMobBanner()
            } else {
                // BottomAdMobBannerAndroidView()
                BottomAdMobAnchoredAdaptiveBannerAndroidView()
            }
        }
    }
}

class HomeViewModelPreview(private val previewState: HomeState) : HomeViewModel {
    override val error: ApiErrorViewModelHelper
        get() = ApiErrorViewModelHelper()

    override fun createDefaultState() = previewState

    override val state: LiveData<HomeState>
        get() = MutableLiveData(createDefaultState())

    override val effect: Flow<HomeEffect>
        get() = flow {}

    override fun event(event: HomeEvent) {
    }
}

@Composable
@Preview
fun HomeScreenPreviewProgress() {
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(HomeState()), isPreview = true)
    }
}

@Composable
@Preview
fun HomeScreenPreviewList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        items = repos.flatMapIndexed { index, repo ->
            if (index == 2) {
                listOf(
                    HomeStateItem.HomeStateAdItem(index.toLong()),
                    HomeStateItem.HomeStateRepoItem(repo)
                )
            } else {
                listOf(HomeStateItem.HomeStateRepoItem(repo))
            }
        }
    )
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(state), isPreview = true)
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkProgress() {
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(HomeState()), isPreview = true)
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        items = repos.flatMapIndexed { index, repo ->
            if (index == 2) {
                listOf(
                    HomeStateItem.HomeStateAdItem(index.toLong()),
                    HomeStateItem.HomeStateRepoItem(repo)
                )
            } else {
                listOf(HomeStateItem.HomeStateRepoItem(repo))
            }
        }
    )
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(state), isPreview = true)
    }
}
