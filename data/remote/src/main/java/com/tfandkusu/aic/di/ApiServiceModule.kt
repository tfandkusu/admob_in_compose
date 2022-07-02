package com.tfandkusu.aic.di

import com.tfandkusu.aic.api.GithubApiServiceBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun providesGithubApiService() = GithubApiServiceBuilder.build()
}
