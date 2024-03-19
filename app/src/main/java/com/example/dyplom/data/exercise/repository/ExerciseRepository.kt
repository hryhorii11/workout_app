package com.example.dyplom.data.exercise.repository

import com.example.dyplom.data.exercise.model.ExerciseUi

interface ExerciseRepository {

  suspend  fun getExercises(): List<ExerciseUi>

  suspend fun addExercise(exerciseUi: ExerciseUi)
}