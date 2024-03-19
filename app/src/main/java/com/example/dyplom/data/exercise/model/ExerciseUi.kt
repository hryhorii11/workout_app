package com.example.dyplom.data.exercise.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ExerciseUi(
    val name: String,
    val category: String,
    val description: String,
    val equipment: String,
    var image: String? = null,
    val id: Long,
    val isChecked: Boolean = false,
) : Parcelable {
    fun toExerciseInWorkout() = ExerciseInWorkout(
        id.toInt(),
        name,
        category,
        description,
        equipment,
        image,
        id = UUID.randomUUID().toString()
    )
}
