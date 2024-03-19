package com.example.dyplom.di

import com.example.dyplom.data.room.AppDatabase
import com.example.dyplom.data.user.UserRepository
import com.example.dyplom.data.workout.repository.WorkoutRepository
import com.example.dyplom.data.workout.repository.WorkoutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkoutRepositoryModule {

    @Provides
    @Singleton
    fun provideWorkoutRepository(db: AppDatabase, userRepository: UserRepository):WorkoutRepository = WorkoutRepositoryImpl(db,userRepository)
}