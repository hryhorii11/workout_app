package com.example.dyplom.presentation.ui.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dyplom.data.exercise.model.ExerciseUi

object ItemCallbackExercise : DiffUtil.ItemCallback<ExerciseUi>() {
    override fun areItemsTheSame(oldItem: ExerciseUi, newItem: ExerciseUi): Boolean
            = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExerciseUi, newItem: ExerciseUi): Boolean {
        return oldItem == newItem
    }
}
