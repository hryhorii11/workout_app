package com.example.dyplom.presentation.ui.workouts.createWorkout


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.databinding.ExerciseInWorkoutItemBinding
import com.example.dyplom.presentation.ui.util.ext.setPhoto
import com.example.dyplom.presentation.ui.workouts.createWorkout.util.ItemCallbackExerciseSettings

class WorkoutExercisesSettingsAdapter(private val clickListener: ExerciseSettingsClickListener) :
    ListAdapter<ExerciseInWorkout, WorkoutExercisesSettingsAdapter.ExerciseViewHolder>(
        ItemCallbackExerciseSettings
    ),
    View.OnClickListener {
    override fun onClick(view: View?) {
        val exercise = view?.tag as ExerciseInWorkout
        when (view.id) {
            R.id.imageViewOptions -> {
                clickListener.onOptionsClick(currentList.indexOf(exercise))
            }

            R.id.textViewRestTime -> {
                Log.i("hui",currentList.indexOf(exercise).toString())
                Log.i("hui",exercise.toString())
                clickListener.onParametersClick(currentList.indexOf(exercise), exercise)
            }

            R.id.textViewSets -> {
                clickListener.onParametersClick(currentList.indexOf(exercise), exercise)
            }

            R.id.textViewKg -> {
                clickListener.onParametersClick(currentList.indexOf(exercise), exercise)
            }
        }

    }

    inner class ExerciseViewHolder(val binding: ExerciseInWorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.editTextComment.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    Log.i("hui","lost focus")
                    clickListener.saveComment(
                        binding.editTextComment.text.toString(),
                        adapterPosition
                    )
                }
            }
        }

        fun bind(exercise: ExerciseInWorkout) {
            with(binding) {
                cardViewExerciseInWorkoutItem.tag = exercise
                imageViewOptions.tag = exercise
                textViewRestTime.tag = exercise
                textViewSets.tag =  exercise
                textViewKg.tag = exercise
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
                editTextComment.setText(exercise.comment)
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
        val binding = ExerciseInWorkoutItemBinding.inflate(inflater, parent, false)
        with(binding) {
            cardViewExerciseInWorkoutItem.setOnClickListener(this@WorkoutExercisesSettingsAdapter)
            imageViewOptions.setOnClickListener(this@WorkoutExercisesSettingsAdapter)
            textViewRestTime.setOnClickListener(this@WorkoutExercisesSettingsAdapter)
            textViewSets.setOnClickListener(this@WorkoutExercisesSettingsAdapter)
            textViewKg.setOnClickListener(this@WorkoutExercisesSettingsAdapter)
            layout.onTapEvent ={  }
        }
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

