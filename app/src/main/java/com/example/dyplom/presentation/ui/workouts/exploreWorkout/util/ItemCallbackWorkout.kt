package com.example.dyplom.presentation.ui.workouts.exploreWorkout.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.dyplom.data.workout.model.Workout

object ItemCallbackWorkout : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem

    }
}