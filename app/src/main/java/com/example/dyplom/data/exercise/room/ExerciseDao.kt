package com.example.dyplom.data.exercise.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.dyplom.data.exercise.room.entity.ExerciseDbEntity

@Dao
interface ExerciseDao {

    @Query("select * from exercises")
    suspend fun getAllExercises():List<ExerciseDbEntity>

    @Upsert(entity = ExerciseDbEntity::class)
    suspend fun addExercise(accountDbEntity: ExerciseDbEntity)

     @Query("select * from exercises where id=:exerciseId")
     fun getExercise(exerciseId: Int): ExerciseDbEntity

}