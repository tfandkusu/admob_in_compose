package com.tfandkusu.aic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tfandkusu.aic.data.local.entity.LocalFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite ORDER BY githubRepoId ASC")
    fun listAsFlow(): Flow<List<LocalFavorite>>

    @Insert
    suspend fun insert(localFavorite: LocalFavorite)

    @Query("DELETE FROM favorite WHERE githubRepoId=:githubRepoId")
    suspend fun delete(githubRepoId: Long)

    @Query("DELETE FROM favorite WHERE githubRepoId NOT IN (SELECT serverId FROM githubRepo)")
    suspend fun deleteNotInGithubRepo()

    @Query("DELETE FROM favorite")
    suspend fun clear()

    @Query("SELECT githubRepoId FROM favorite ORDER BY githubRepoId ASC")
    suspend fun listGithubIds(): List<Long>
}
