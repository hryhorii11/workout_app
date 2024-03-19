package com.example.dyplom.presentation.ui.workouts.createWorkout.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dyplom.data.exercise.model.ExerciseInWorkout


object ItemCallbackExerciseSettings : DiffUtil.ItemCallback<ExerciseInWorkout>() {
    override fun areItemsTheSame(oldItem: ExerciseInWorkout, newItem: ExerciseInWorkout): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExerciseInWorkout, newItem: ExerciseInWorkout): Boolean {
        return oldItem == newItem
    }
}
