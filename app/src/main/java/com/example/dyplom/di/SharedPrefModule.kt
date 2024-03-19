package com.example.dyplom.di

import android.app.Application
import android.content.Context
import com.example.dyplom.data.preferences.PreferencesRepository
import com.example.dyplom.data.preferences.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPref( app: Context):PreferencesRepository=PreferencesRepositoryImpl(app)
}