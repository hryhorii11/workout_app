package com.example.dyplom.data.workout.repository

import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.data.workout.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun addWorkout(workout: Workout)
    fun getAllWorkouts(): Flow<Result<List<Workout>>>
    fun getWorkoutExercises(workoutId:String): Flow<Result<List<ExerciseInWorkout>>>
    fun saveUserWorkout(workoutId:String):Flow<Result<Unit>>
    fun getUserWorkouts():Flow<Result<List<Workout>>>
    fun deleteWorkout(workoutId: String)
}