package com.example.dyplom.data.exercise.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dyplom.data.exercise.model.Equipment
import com.example.dyplom.data.exercise.model.ExerciseUi

@Entity(
    tableName = "exercises"
)
data class ExerciseDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    val equipment: String,
    @ColumnInfo(name = "image") val image: String?,
) {
    fun toExercise(): ExerciseUi = ExerciseUi(
        name,
        category,
        description,
        equipment,
        image,
        id
    )

    companion object {
        fun createEntity(exerciseUi: ExerciseUi): ExerciseDbEntity {
            return ExerciseDbEntity(
                exerciseUi.id,
                exerciseUi.name,
                exerciseUi.category,
                exerciseUi.description,
                exerciseUi.equipment,
                exerciseUi.image,
            )
        }
    }
}
