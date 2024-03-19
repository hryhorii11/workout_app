package com.example.dyplom.presentation.ui.workouts.createWorkout

import com.example.dyplom.data.exercise.model.ExerciseInWorkout

interface ExerciseSettingsClickListener {
    fun saveComment(comment: String, position: Int)
    fun onOptionsClick(exercisePosition: Int)
    fun onParametersClick(position: Int, exerciseInWorkout: ExerciseInWorkout)
}