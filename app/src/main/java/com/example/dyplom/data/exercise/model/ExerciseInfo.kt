package com.example.dyplom.data.exercise.model

data class ExerciseInfo(
    val category: Category,
    val equipment: List<Equipment>,
    val images: List<ExerciseImage>,
    val exercises: List<Exercise>
)
data class Equipment(
    val name: String
)
data class Category(
    val name: String
)

data class ExerciseImage(
    val image: String
)
data class Exercise(
    val name: String,
    val description: String,
    val id: Int,
    val language:Int
)