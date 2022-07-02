package com.tfandkusu.aic.data.repository

import com.tfandkusu.aic.data.local.CreatedLocalDataStore
import com.tfandkusu.aic.data.local.GithubRepoLocalDataStore
import com.tfandkusu.aic.data.local.entity.LocalCreated
import com.tfandkusu.aic.data.remote.GithubRemoteDataStore
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.util.CurrentTimeGetter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepoRepository {

    suspend fun isCacheExpired(): Boolean

    suspend fun fetch()

    fun listAsFlow(): Flow<List<GithubRepo>>

    suspend fun favorite(id: Long, on: Boolean)
}

class GithubRepoRepositoryImpl @Inject constructor(
    private val remoteDataStore: GithubRemoteDataStore,
    private val localDataStore: GithubRepoLocalDataStore,
    private val createdLocalDataStore: CreatedLocalDataStore,
    private val currentTimeGetter: CurrentTimeGetter
) : GithubRepoRepository {

    companion object {
        private const val CACHE_TIME = 60 * 60 * 1000
    }

    override suspend fun isCacheExpired(): Boolean {
        val saveTime = createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO)
        val elapsedTime = currentTimeGetter.get() - saveTime
        return elapsedTime > CACHE_TIME
    }

    override suspend fun fetch() {
        val repos = remoteDataStore.listRepositories()
        localDataStore.save(repos)
    }

    override fun listAsFlow() = localDataStore.listAsFlow()

    override suspend fun favorite(id: Long, on: Boolean) {
        localDataStore.favorite(id, on)
    }
}
