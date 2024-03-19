package com.example.dyplom.presentation.ui.workouts.workoutDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dyplom.data.UIState
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.data.workout.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<UIState<List<ExerciseInWorkout>>>(UIState.Idle())
    val exercises = _exercises.asStateFlow()



    fun setExercises(workoutId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutRepository.getWorkoutExercises(workoutId).collect(){
                if(it.isSuccess) _exercises.value= UIState.Success(it.getOrNull()!!)
                else _exercises.value=UIState.Error(it.exceptionOrNull()?.message?:"Error")
            }

        }
    }

}