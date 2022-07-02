package com.tfandkusu.template.di

import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfoUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindOnClickAboutUseCase(
        useCase: InfoOnClickAboutUseCaseImpl
    ): InfoOnClickAboutUseCase
}
