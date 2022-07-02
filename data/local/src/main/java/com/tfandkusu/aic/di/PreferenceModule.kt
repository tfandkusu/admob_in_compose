package com.tfandkusu.aic.di

import android.content.Context
import com.tfandkusu.aic.data.local.pref.MyPreferenceBuilder
import com.tfandkusu.aic.data.local.pref.MyPreferenceManager
import com.tfandkusu.aic.data.local.pref.MyPreferenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context
    ): MyPreferenceManager {
        return MyPreferenceManagerImpl(MyPreferenceBuilder.build(context))
    }
}
