package com.example.dyplom.presentation.ui.workouts.createWorkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.data.user.UserRepository
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.data.workout.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _exercises = MutableLiveData<List<ExerciseInWorkout>>()
    val exercises = _exercises

    init {
        _exercises.value = emptyList()
    }


    fun addExercise(exercises: List<ExerciseUi>) {
        _exercises.value = _exercises.value?.toMutableList().also {
            it?.addAll(exercises.map { ex ->
                ex.toExerciseInWorkout()
            })
        }

    }

    fun saveComment(comment: String, position: Int) {
        Log.i("hui",comment)
        _exercises.value = _exercises.value?.toMutableList().also { list ->
            list?.set(position, list[position].copy(comment = comment))
        }

    }

    fun deleteExercise(exercisePosition: Int) {
        _exercises.value = _exercises.value?.toMutableList().also {
            it?.removeAt(exercisePosition)
        }
    }

    fun replaceExercise(replacementExercisePosition: Int, exercise: ExerciseUi) {
        if (replacementExercisePosition > 0 && _exercises.value!!.size > replacementExercisePosition) {
            _exercises.value = _exercises.value?.toMutableList().also {
                it?.set(replacementExercisePosition, exercise.toExerciseInWorkout())
            }
        }
    }

    fun addParameters(position: Int, exerciseInWorkout: ExerciseInWorkout) {
        _exercises.value = _exercises.value?.toMutableList().also {
            it?.set(position, exerciseInWorkout)
        }
    }

    fun saveWorkout(editTextWorkoutName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _exercises.value?.let {
                userRepository.currentUser?.displayName?.let { it1 ->
                    Workout(
                        UUID.randomUUID().toString(), editTextWorkoutName, it1, it
                    )
                }
            }?.let {
                workoutRepository.addWorkout(
                    it
                )
            }
        }
    }

    fun clearList() {
        _exercises.value = emptyList()
    }
}