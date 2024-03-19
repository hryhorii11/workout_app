package com.example.dyplom.data.exercise.model

import java.util.UUID

data class ExerciseInWorkout(
    val exerciseId: Int = 0,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val equipment: String = "",
    var image: String? = null,
    val numberOfSets: Int? = 1,
    val restTime: String = "00:00",
    val kg: Int? = 0,
    val comment: String? = null,
    val id: String = ""
) {
    constructor() : this(name = "")

    fun toExercise() =
        ExerciseUi(name, category, description, equipment, image, exerciseId.toLong())
}