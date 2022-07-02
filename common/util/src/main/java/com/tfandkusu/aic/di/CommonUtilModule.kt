package com.tfandkusu.aic.di

import com.tfandkusu.aic.util.CurrentTimeGetter
import com.tfandkusu.aic.util.CurrentTimeGetterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonUtilModule {
    @Binds
    @Singleton
    abstract fun bindCurrentTimeGetter(currentTimeGetter: CurrentTimeGetterImpl): CurrentTimeGetter
}
