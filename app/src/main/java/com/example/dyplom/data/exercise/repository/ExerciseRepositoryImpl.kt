package com.example.dyplom.data.exercise.repository

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.data.exercise.room.ExerciseDao
import com.example.dyplom.data.exercise.room.entity.ExerciseDbEntity
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val app: Application,
    private val exerciseDao: ExerciseDao
) :
    ExerciseRepository {


    override suspend fun getExercises(): List<ExerciseUi> {
        return exerciseDao.getAllExercises().map { it.toExercise() }
    }

    override suspend fun addExercise(exerciseUi: ExerciseUi) {
        exerciseDao.addExercise(ExerciseDbEntity.createEntity(exerciseUi))
    }
}