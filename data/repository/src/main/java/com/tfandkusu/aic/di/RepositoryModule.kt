package com.tfandkusu.aic.di

import com.tfandkusu.aic.data.repository.GithubRepoRepository
import com.tfandkusu.aic.data.repository.GithubRepoRepositoryImpl
import com.tfandkusu.aic.data.repository.NumberOfStartsRepository
import com.tfandkusu.aic.data.repository.NumberOfStartsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGithubRepoRepository(
        repository: GithubRepoRepositoryImpl
    ): GithubRepoRepository

    @Binds
    @Singleton
    abstract fun bindNumberOfStartsRepository(
        repository: NumberOfStartsRepositoryImpl
    ): NumberOfStartsRepository
}
