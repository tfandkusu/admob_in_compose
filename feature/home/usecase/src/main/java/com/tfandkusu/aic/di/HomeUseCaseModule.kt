package com.tfandkusu.aic.di

import com.tfandkusu.aic.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.aic.usecase.home.HomeFavoriteUseCaseImpl
import com.tfandkusu.aic.usecase.home.HomeLoadUseCase
import com.tfandkusu.aic.usecase.home.HomeLoadUseCaseImpl
import com.tfandkusu.aic.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.aic.usecase.home.HomeOnCreateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindHomeLoadUseCase(
        useCase: HomeLoadUseCaseImpl
    ): HomeLoadUseCase

    @Binds
    @Singleton
    abstract fun bindOnCreateUseCase(
        useCase: HomeOnCreateUseCaseImpl
    ): HomeOnCreateUseCase

    @Binds
    @Singleton
    abstract fun bindFavoriteUseCase(
        useCase: HomeFavoriteUseCaseImpl
    ): HomeFavoriteUseCase
}
