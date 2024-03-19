package com.example.dyplom.data.exercise.model

data class ExerciseResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)