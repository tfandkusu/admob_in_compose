package com.tfandkusu.aic.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tfandkusu.aic.data.local.dao.CreatedDao
import com.tfandkusu.aic.data.local.dao.FavoriteDao
import com.tfandkusu.aic.data.local.dao.GithubRepoDao
import com.tfandkusu.aic.data.local.entity.LocalCreated
import com.tfandkusu.aic.data.local.entity.LocalFavorite
import com.tfandkusu.aic.data.local.entity.LocalGithubRepo

@Database(
    entities = [LocalGithubRepo::class, LocalCreated::class, LocalFavorite::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao

    abstract fun createdDao(): CreatedDao

    abstract fun favoriteDao(): FavoriteDao
}
