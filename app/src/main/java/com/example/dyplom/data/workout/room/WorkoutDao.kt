package com.example.dyplom.data.workout.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.data.workout.room.entity.WorkoutDbEntity
import com.example.dyplom.data.workout.room.entity.WorkoutExercisesDbEntity
import kotlinx.coroutines.selects.select


@Dao
interface WorkoutDao {

    @Query("select * from workouts")
    fun getALlWorkouts(): List<WorkoutDbEntity>

    @Insert
    fun addWorkout(workout: WorkoutDbEntity): Long

    @Insert
    fun addWorkoutExercises(workoutExercises: List<WorkoutExercisesDbEntity>)

    @Query("select * from workout_exercises where workout_id=:workoutId")
    fun getWorkoutExercises(workoutId: Int): List<WorkoutExercisesDbEntity>


}