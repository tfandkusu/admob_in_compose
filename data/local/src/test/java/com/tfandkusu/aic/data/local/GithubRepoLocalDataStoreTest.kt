package com.tfandkusu.aic.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tfandkusu.aic.catalog.GitHubRepoCatalog
import com.tfandkusu.aic.data.local.db.AppDatabaseBuilder
import com.tfandkusu.aic.data.local.entity.LocalCreated
import com.tfandkusu.aic.util.CurrentTimeGetter
import com.tfandkusu.aic.util.parseUTC
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubRepoLocalDataStoreTest {
    @MockK(relaxed = true)
    private lateinit var currentTimeGetter: CurrentTimeGetter

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val db = AppDatabaseBuilder.build(context, true)

    private lateinit var localDataStore: GithubRepoLocalDataStore

    private lateinit var createdLocalDataStore: CreatedLocalDataStore

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataStore = GithubRepoLocalDataStoreImpl(db, currentTimeGetter)
        createdLocalDataStore = CreatedLocalDataStoreImpl(db)
    }

    @Test
    fun test() = runBlocking {
        // Check empty
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe 0L
        localDataStore.listAsFlow().first() shouldBe listOf()
        // Check save
        val firstTime = parseUTC("2021-11-15T01:12:45Z")
        val secondTime = parseUTC("2021-11-15T03:00:00Z")
        every {
            currentTimeGetter.get()
        } returns firstTime.time andThen secondTime.time
        val (repo1, repo2, repo3) = GitHubRepoCatalog.getList()
        localDataStore.save(listOf(repo1, repo3, repo2))
        localDataStore.listAsFlow().first() shouldBe listOf(repo1, repo2, repo3)
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe firstTime.time
        // Second save
        localDataStore.save(listOf(repo1.copy(name = "Edited"), repo3, repo2))
        localDataStore.listAsFlow().first() shouldBe listOf(
            repo1.copy(name = "Edited"),
            repo2,
            repo3
        )
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe secondTime.time
        // Favorite on
        localDataStore.favorite(repo2.id, true)
        localDataStore.listAsFlow().first() shouldBe listOf(
            repo1.copy(name = "Edited"),
            repo2.copy(favorite = true),
            repo3
        )
        // Favorite off
        localDataStore.favorite(repo2.id, false)
        localDataStore.listAsFlow().first() shouldBe listOf(
            repo1.copy(name = "Edited"),
            repo2,
            repo3
        )
        // Delete favorite
        localDataStore.favorite(repo1.id, true)
        localDataStore.favorite(repo3.id, true)
        localDataStore.save(listOf(repo1, repo2))
        localDataStore.listFavoriteGitHubRepoIds() shouldBe listOf(repo1.id)
        // Check clear
        localDataStore.clear()
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe 0
    }
}
