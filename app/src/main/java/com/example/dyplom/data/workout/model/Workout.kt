package com.example.dyplom.data.workout.model

import com.example.dyplom.data.UIState
import com.example.dyplom.data.exercise.model.ExerciseInWorkout

data class Workout(
    val id: String,
    val name: String,
    val author: String,
    val exerciseList: List<ExerciseInWorkout> = emptyList(),
){
    constructor() : this("", "", "")
}
