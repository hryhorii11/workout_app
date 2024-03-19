package com.example.dyplom.di

import android.app.Application
import com.example.dyplom.data.exercise.repository.ExerciseRepository
import com.example.dyplom.data.exercise.repository.ExerciseRepositoryImpl
import com.example.dyplom.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExerciseRepositoryModule {
    @Provides
    @Singleton
    fun provideExerciseRepository(app: Application, db: AppDatabase): ExerciseRepository =
        ExerciseRepositoryImpl(app, db.getExerciseDao())
}