package com.example.dyplom.di

import com.example.dyplom.data.preferences.PreferencesRepository
import com.example.dyplom.data.preferences.PreferencesRepositoryImpl
import com.example.dyplom.data.user.UserRepository
import com.example.dyplom.data.user.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth,sharedPref:PreferencesRepositoryImpl): UserRepository =
        UserRepositoryImpl(firebaseAuth, sharedPref)

}