package com.example.dyplom.data.api

import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.data.exercise.model.ExerciseImage
import com.example.dyplom.data.exercise.model.ExerciseInfo
import com.example.dyplom.data.exercise.model.ExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ExerciseService {

    @GET("exercise/?language=2&limit=400")
    suspend fun getExercises(@Header("Authorization") token: String): ExerciseResponse<ExerciseUi>

    @GET("exerciseimage/?limit=300")
    suspend fun getExerciseImages(@Header("Authorization") token: String): ExerciseResponse<ExerciseImage>

    @GET("exercisebaseinfo/?limit=500")
    suspend fun getExerciseInfo(@Header("Authorization") token: String): ExerciseResponse<ExerciseInfo>

    companion object {
        const val BASE_URL = "https://wger.de/api/v2/"
    }
}