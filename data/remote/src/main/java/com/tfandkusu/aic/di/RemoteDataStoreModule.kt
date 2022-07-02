package com.tfandkusu.aic.di

import com.tfandkusu.aic.data.remote.GithubRemoteDataStore
import com.tfandkusu.aic.data.remote.GithubRemoteDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataStoreModule {
    @Binds
    @Singleton
    abstract fun provideGithubRemoteDataStore(
        remoteDataStore: GithubRemoteDataStoreImpl
    ): GithubRemoteDataStore
}
