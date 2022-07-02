package com.tfandkusu.aic.data.remote

import com.tfandkusu.aic.api.GithubApiService
import com.tfandkusu.aic.api.GithubApiServiceBuilder
import com.tfandkusu.aic.error.NetworkErrorException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class GithubRemoteDataStoreTest {

    @MockK(relaxed = true)
    private lateinit var service: GithubApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun listRepositoriesSuccess() = runBlocking {
        val remoteDataStore = GithubRemoteDataStoreImpl(GithubApiServiceBuilder.build())
        val list = remoteDataStore.listRepositories()
        // Check page 1 exists
        val target = list.firstOrNull { it.name == "groupie_sticky_header_sample" }
        target shouldNotBe null
        target?.also {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
            it.id shouldBe 320900929
            it.description.indexOf("groupie") shouldBeGreaterThan 0
            (it.updatedAt > sdf.parse("2021/01/20 04:00:00")) shouldBe true
            it.language shouldBe "Java"
            it.htmlUrl shouldBe "https://github.com/tfandkusu/groupie_sticky_header_sample"
            it.fork shouldBe false
        }
        // Check page 2 exists
        val target2 = list.firstOrNull { it.name == "watch_movie_dir_gif" }
        target2 shouldNotBe null
    }

    @Test
    fun listRepositoriesNetworkError() = runBlocking {
        coEvery {
            service.listRepos(1)
        } throws IOException()
        val remoteDataStore = GithubRemoteDataStoreImpl(service)
        shouldThrow<NetworkErrorException> {
            remoteDataStore.listRepositories()
        }
        Unit
    }
}
