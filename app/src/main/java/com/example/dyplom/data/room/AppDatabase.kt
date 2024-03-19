package com.example.dyplom.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dyplom.data.exercise.room.ExerciseDao
import com.example.dyplom.data.exercise.room.entity.ExerciseDbEntity
import com.example.dyplom.data.workout.room.WorkoutDao
import com.example.dyplom.data.workout.room.entity.WorkoutDbEntity
import com.example.dyplom.data.workout.room.entity.WorkoutExercisesDbEntity

@Database(
    version = 3,
    entities = [
        ExerciseDbEntity::class,
        WorkoutDbEntity::class,
        WorkoutExercisesDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getExerciseDao():ExerciseDao

    abstract fun getWorkoutDao():WorkoutDao
}