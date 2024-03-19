package com.example.dyplom.data.workout.repository

import android.util.Log
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.data.room.AppDatabase
import com.example.dyplom.data.user.UserRepository
import com.example.dyplom.data.utils.await
import com.example.dyplom.data.workout.model.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WorkoutRepositoryImpl(
    private val db: AppDatabase,
    private val userRepository: UserRepository
) : WorkoutRepository {

    private val fireStore = Firebase.firestore
    override fun addWorkout(workout: Workout) {
        fireStore.collection("workouts").document(workout.id).set(workout)
//        val workoutId = db.getWorkoutDao().addWorkout(WorkoutDbEntity.createEntity(workout))
//        db.getWorkoutDao().addWorkoutExercises(
//            workout.exerciseList.map {
//                WorkoutExercisesDbEntity(
//                    workoutId.toInt(),
//                    it.exerciseId,
//                    it.restTime,
//                    it.numberOfSets,
//                    it.kg
//                )
//            }
//        )
    }

    override fun getAllWorkouts(): Flow<Result<List<Workout>>> {
        return flow {
            val result = fireStore.collection("workouts").get().await()
            val list = result.toObjects(Workout::class.java)
            emit(Result.success(list))
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.failure(it))
        }

    }

    override fun getWorkoutExercises(workoutId: String): Flow<Result<List<ExerciseInWorkout>>> {
        return flow {
            val result =
                fireStore.collection("workouts").document(workoutId).get().await()
            emit((result.toObject(Workout::class.java)?.let { Result.success(it.exerciseList) }!!))
        }

    }

    override fun saveUserWorkout(workoutId: String): Flow<Result<Unit>> {
        return flow {
            fireStore.collection("user_workouts").document().set(
                hashMapOf(
                    "user_id" to userRepository.currentUser?.uid,
                    "workout_id" to workoutId
                )
            )
            emit(Result.success(Unit))
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.failure(it))
        }
    }

    override fun getUserWorkouts(): Flow<Result<List<Workout>>> {
        return flow {
            val junction = fireStore.collection("user_workouts")
                .whereEqualTo("user_id", userRepository.currentUser?.uid)
                .get().await()
            val userWorkouts = junction.documents
                .filter { it.exists() }
                .map {
                    fireStore.document("workouts/${it.getString("workout_id")!!}").get().await()
                }
            emit(Result.success(userWorkouts.map { it.toObject(Workout::class.java)!! }))
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.failure(it))
        }
    }

    override fun deleteWorkout(workoutId: String) {
          Log.i("hui","deleting")
        fireStore.collection("user_workouts")
            .whereEqualTo("workout_id", workoutId)
            .whereEqualTo("user_id", userRepository.currentUser?.uid).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete().addOnSuccessListener {

                    }
                }
            }


    }

}