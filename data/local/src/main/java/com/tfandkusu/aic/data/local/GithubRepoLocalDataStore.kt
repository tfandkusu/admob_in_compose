package com.tfandkusu.aic.data.local

import androidx.room.withTransaction
import com.tfandkusu.aic.data.local.db.AppDatabase
import com.tfandkusu.aic.data.local.entity.LocalCreated
import com.tfandkusu.aic.data.local.entity.LocalFavorite
import com.tfandkusu.aic.data.local.entity.LocalGithubRepo
import com.tfandkusu.aic.model.GithubRepo
import com.tfandkusu.aic.util.CurrentTimeGetter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date
import javax.inject.Inject

interface GithubRepoLocalDataStore {
    suspend fun save(githubRepos: List<GithubRepo>)

    fun listAsFlow(): Flow<List<GithubRepo>>

    suspend fun favorite(serverId: Long, on: Boolean)

    /**
     * For test
     */
    suspend fun listFavoriteGitHubRepoIds(): List<Long>

    suspend fun clear()
}

class GithubRepoLocalDataStoreImpl @Inject constructor(
    private val db: AppDatabase,
    private val currentTimeGetter: CurrentTimeGetter
) :
    GithubRepoLocalDataStore {

    private val githubRepoDao = db.githubRepoDao()

    private val createdDao = db.createdDao()

    private val favoriteDao = db.favoriteDao()

    override suspend fun save(githubRepos: List<GithubRepo>) {
        db.withTransaction {
            githubRepoDao.clear()
            githubRepos.map {
                LocalGithubRepo(
                    0L,
                    it.id,
                    it.name,
                    it.description,
                    it.updatedAt.time,
                    it.language,
                    it.htmlUrl,
                    it.fork
                )
            }.map {
                githubRepoDao.insert(it)
            }
            createdDao.insert(
                LocalCreated(
                    createdDao.get(LocalCreated.KIND_GITHUB_REPO)?.id ?: 0L,
                    LocalCreated.KIND_GITHUB_REPO,
                    currentTimeGetter.get()
                )
            )
            favoriteDao.deleteNotInGithubRepo()
        }
    }

    override fun listAsFlow(): Flow<List<GithubRepo>> {
        return combine(githubRepoDao.listAsFlow(), favoriteDao.listAsFlow()) { repos, favoList ->
            val favoSet = favoList.map { it.githubRepoId }.toSet()
            repos.map {
                GithubRepo(
                    it.serverId,
                    it.name,
                    it.description,
                    Date(it.updatedAt),
                    it.language,
                    it.htmlUrl,
                    it.fork,
                    favoSet.contains(it.serverId)
                )
            }
        }
    }

    override suspend fun favorite(serverId: Long, on: Boolean) {
        if (on) {
            favoriteDao.insert(LocalFavorite(0L, serverId))
        } else {
            favoriteDao.delete(serverId)
        }
    }

    override suspend fun listFavoriteGitHubRepoIds(): List<Long> {
        return favoriteDao.listGithubIds()
    }

    override suspend fun clear() {
        db.withTransaction {
            githubRepoDao.clear()
            createdDao.delete(LocalCreated.KIND_GITHUB_REPO)
        }
    }
}
