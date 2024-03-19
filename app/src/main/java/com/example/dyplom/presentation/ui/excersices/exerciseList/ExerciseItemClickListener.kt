package com.example.dyplom.presentation.ui.excersices.exerciseList

import com.example.dyplom.data.exercise.model.ExerciseUi

interface ExerciseItemClickListener {

    fun onClick(exercise: ExerciseUi)
    fun onDetail(exercise: ExerciseUi)

}