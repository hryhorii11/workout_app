package com.example.dyplom.presentation.ui.workouts.exploreWorkout

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.UIState
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.databinding.WorkoutItemBinding
import com.example.dyplom.presentation.ui.util.ext.hide
import com.example.dyplom.presentation.ui.util.ext.show
import com.example.dyplom.presentation.ui.workouts.exploreWorkout.util.ItemCallbackWorkout
import kotlinx.coroutines.flow.MutableStateFlow

class ExploreWorkoutAdapter(private val clickListener: WorkoutItemClickListener) :
    ListAdapter<Workout, ExploreWorkoutAdapter.WorkoutViewHolder>(ItemCallbackWorkout),
    View.OnClickListener {

    private var _states = HashMap<String, UIState<Unit>>(hashMapOf())

    override fun onClick(view: View?) {
        val workout = view?.tag as Workout
        when (view.id) {
            R.id.cardViewItemWorkoutItem -> clickListener.onClick(workout)
            R.id.buttonSaveWorkout -> {
                clickListener.saveWorkout(workout)

            }
        }
    }

    inner class WorkoutViewHolder(private val binding: WorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout, state: UIState<Unit> = UIState.Idle()) {
            with(binding) {
                cardViewItemWorkoutItem.tag = workout
                buttonSaveWorkout.tag = workout
                textViewWorkoutName.text = workout.name
                textViewWorkoutAuthor.text = workout.author
                setState(state)
            }
        }

        private fun setState(state: UIState<Unit>) {
            with(binding) {
                when (state) {
                    is UIState.Loading -> {
                        progressBarWorkoutItem.show()
                        buttonSaveWorkout.hide()
                        imageViewAddedWorkout.hide()
                    }

                    is UIState.Success -> {
                        progressBarWorkoutItem.hide()
                        buttonSaveWorkout.hide()
                        imageViewAddedWorkout.show()
                    }

                    else -> {
                        imageViewAddedWorkout.hide()
                        progressBarWorkoutItem.hide()
                        buttonSaveWorkout.show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WorkoutItemBinding.inflate(inflater, parent, false)
        binding.cardViewItemWorkoutItem.setOnClickListener(this)
        binding.buttonSaveWorkout.setOnClickListener(this)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(currentList[position],_states[currentList[position].id]?:UIState.Idle())
    }

    fun setStates(states: HashMap<String, UIState<Unit>>) {
        _states = states
        states.forEach { (key, value) ->

            notifyItemChanged(currentList.indexOf(currentList.find { it.id == key }))
        }

    }

}