package com.example.dyplom.presentation.ui.workouts.exploreWorkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExploreWorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _workoutListState = MutableStateFlow<UIState<List<Workout>>>(UIState.Idle())
    val workoutListState = _workoutListState.asStateFlow()
    private var workoutList = mutableListOf<Workout>()
    private val _states = MutableStateFlow<HashMap<String, UIState<Unit>>>(hashMapOf())
    val states = _states.asStateFlow()

    //  val _states=MutableLiveData<HashMap<String, UIState<Unit>>>(hashMapOf())
    fun setWorkoutList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllWorkouts().collect {
                if (it.isSuccess) {
                    _workoutListState.value = UIState.Success(it.getOrNull()!!)
                    workoutList = (it.getOrNull() as MutableList<Workout>?)!!
                } else _workoutListState.value =
                    UIState.Error(it.exceptionOrNull()?.message ?: "error")
            }

        }
    }

    //    fun saveWorkout(workout: Workout) {
//        _states.value = _states.value.also {
//            it?.set(workout.id, UIState.Loading())
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            var response=false
//            repository.saveUserWorkout(workout.id).collect() {
//                response=it.isSuccess
//
//            }
//            withContext(Dispatchers.Main){
//                _states.value = if (response) _states.value.also { states ->
//                    states?.set(workout.id, UIState.Success(Unit))
//                }
//                else _states.value.also { states ->
//                    states?.set(workout.id, UIState.Idle())
//                }
//            }
//        }
//    }
    fun saveWorkout(workout: Workout) {

        var map = _states.value.toMutableMap()
        map[workout.id] = UIState.Loading()
        _states.value = map as HashMap<String, UIState<Unit>>
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUserWorkout(workout.id).collect() {
                map = _states.value.toMutableMap()
                _states.value = if (it.isSuccess) {
                    map[workout.id] = UIState.Success(Unit)
                    map as HashMap<String, UIState<Unit>>
                } else {
                    map[workout.id] = UIState.Idle()
                    map as HashMap<String, UIState<Unit>>
                }
            }

        }
        Log.i("hui2", _states.value.toString())

    }
}