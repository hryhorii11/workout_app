package com.example.dyplom.presentation.ui.workouts.workoutList

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dyplom.data.UIState
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.data.workout.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserWorkoutListViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {


    private val _workoutList = MutableStateFlow<UIState<List<Workout>>>(UIState.Idle())
    val workoutList = _workoutList.asStateFlow()

    private var originalList: List<Workout> = emptyList()
    fun setWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
           _workoutList.value = UIState.Loading()
            repository.getUserWorkouts().collect() {
                if (it.isSuccess) {
                    _workoutList.value = UIState.Success(it.getOrNull()!!)
                    originalList = it.getOrNull()!!.toMutableList()
                } else _workoutList.value = UIState.Error(it.exceptionOrNull()?.message!!)
            }
        }
    }

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkout(workoutId)
            originalList=originalList.toMutableList().also {
                it.remove(it.find {workout ->
                    workout.id == workoutId })
            }
            _workoutList.value=UIState.Success(originalList)
        }
    }
}