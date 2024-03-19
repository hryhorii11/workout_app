package com.example.dyplom.data.workout.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dyplom.data.workout.model.Workout

@Entity(tableName = "workouts")
data class WorkoutDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val author: String,
)
{
   fun toWorkout()=Workout(id.toString(),name,author, emptyList())

    companion object{
        fun createEntity(workout: Workout) =WorkoutDbEntity(0,workout.name,workout.author)
    }
}