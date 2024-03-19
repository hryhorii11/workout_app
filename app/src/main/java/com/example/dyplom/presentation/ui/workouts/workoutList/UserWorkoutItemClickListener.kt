package com.example.dyplom.presentation.ui.workouts.workoutList

import com.example.dyplom.data.workout.model.Workout

interface UserWorkoutItemClickListener {
    fun onClick(workout: Workout)
    fun onOptionsClick(workoutId: String)
}