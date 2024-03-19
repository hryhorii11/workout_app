package com.example.dyplom.presentation.ui.workouts.workoutDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.exercise.model.Exercise
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.databinding.ExerciseInWorkoutDetailItemBinding
import com.example.dyplom.databinding.ExerciseInWorkoutItemBinding
import com.example.dyplom.presentation.ui.util.ext.hide
import com.example.dyplom.presentation.ui.util.ext.setPhoto
import com.example.dyplom.presentation.ui.workouts.createWorkout.ExerciseSettingsClickListener
import com.example.dyplom.presentation.ui.workouts.createWorkout.util.ItemCallbackExerciseSettings

class WorkoutDetailAdapter(private val clickListener: ExerciseInWorkoutDetailClickListener) :
    ListAdapter<ExerciseInWorkout, WorkoutDetailAdapter.ExerciseViewHolder>(
        ItemCallbackExerciseSettings
    ),
    View.OnClickListener {
    override fun onClick(view: View?) {
        val exercise = view?.tag as ExerciseInWorkout
        when (view.id) {
            R.id.cardViewExerciseInWorkoutItem -> {
                clickListener.onClick(exercise.toExercise())
            }
        }
    }

    inner class ExerciseViewHolder(val binding: ExerciseInWorkoutDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(exercise: ExerciseInWorkout) {
            with(binding) {
                cardViewExerciseInWorkoutItem.tag = exercise
                textViewName.text = exercise.name
                textViewRestTime.text = buildString {
                    append("Rest time:")
                    append(exercise.restTime)
                }
                textViewSets.text = buildString {
                    append("Sets:")
                    if (exercise.numberOfSets != null)
                        append(exercise.numberOfSets)
                }
                textViewKg.text = buildString {
                    append("Kg:")
                    if (exercise.kg != null)
                        append(exercise.kg)
                }
                if (!exercise.comment.isNullOrBlank()) {
                    editTextComment.text = exercise.comment
                } else editTextComment.hide()
                if (exercise.image != null) imageViewExerciseImage.setPhoto(exercise.image!!)
                else imageViewExerciseImage.setPhoto()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExerciseInWorkoutDetailItemBinding.inflate(inflater, parent, false)
        binding.cardViewExerciseInWorkoutItem.setOnClickListener(this)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}