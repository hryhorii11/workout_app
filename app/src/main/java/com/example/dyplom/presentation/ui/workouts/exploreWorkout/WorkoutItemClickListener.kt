package com.example.dyplom.presentation.ui.workouts.exploreWorkout

import com.example.dyplom.data.workout.model.Workout

interface WorkoutItemClickListener {
    fun onClick(workout: Workout)
    fun saveWorkout(workout: Workout)
}