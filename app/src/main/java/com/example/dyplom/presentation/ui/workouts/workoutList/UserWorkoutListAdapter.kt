package com.example.dyplom.presentation.ui.workouts.workoutList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.databinding.UserWorkoutItemBinding
import com.example.dyplom.databinding.WorkoutItemBinding
import com.example.dyplom.presentation.ui.workouts.exploreWorkout.util.ItemCallbackWorkout

class UserWorkoutListAdapter(private val clickListener: UserWorkoutItemClickListener) :
    ListAdapter<Workout, UserWorkoutListAdapter.WorkoutViewHolder>(ItemCallbackWorkout),
    View.OnClickListener {
    override fun onClick(view: View?) {
        val workout = view?.tag as Workout
        when (view.id) {
            R.id.cardViewItemWorkoutItem -> clickListener.onClick(workout)
            R.id.imageViewOptions -> clickListener.onOptionsClick(workout.id)
        }
    }

    inner class WorkoutViewHolder(private val binding: UserWorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            with(binding) {
                cardViewItemWorkoutItem.tag = workout
                imageViewOptions.tag=workout
                textViewWorkoutName.text = workout.name
                textViewWorkoutAuthor.text = workout.author
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = UserWorkoutItemBinding.inflate(inflater, parent, false)
        binding.cardViewItemWorkoutItem.setOnClickListener(this)
        binding.imageViewOptions.setOnClickListener(this)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}