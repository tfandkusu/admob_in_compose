package com.tfandkusu.aic.di

import com.tfandkusu.aic.data.local.CreatedLocalDataStore
import com.tfandkusu.aic.data.local.CreatedLocalDataStoreImpl
import com.tfandkusu.aic.data.local.GithubRepoLocalDataStore
import com.tfandkusu.aic.data.local.GithubRepoLocalDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataStoreModule {
    @Binds
    @Singleton
    abstract fun provideGithubRepoLocalDataStore(
        localDataStore: GithubRepoLocalDataStoreImpl
    ): GithubRepoLocalDataStore

    @Binds
    @Singleton
    abstract fun provideCreatedLocalDataStore(
        localDataStore: CreatedLocalDataStoreImpl
    ): CreatedLocalDataStore
}
