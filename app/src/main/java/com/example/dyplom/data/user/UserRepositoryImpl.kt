package com.example.dyplom.data.user

import android.net.Uri
import android.util.Log
import com.example.dyplom.data.preferences.PreferencesRepository
import com.example.dyplom.data.user.model.User
import com.example.dyplom.data.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val sharePrefRepository: PreferencesRepository
) : UserRepository {

    private val database = Firebase.firestore
    private val storage = Firebase.storage

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun signIn(email: String, password: String): Flow<Result<Unit>> {
        return flow {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Result.success(Unit))
            currentUser?.displayName?.let {
                sharePrefRepository.saveAuthorizedUser(
                    it,
                    email,
                    password
                )
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.failure(it))
        }
    }

    override fun signUp(name: String, email: String, password: String): Flow<Result<Unit>> {
        return flow {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )
            emit(Result.success(Unit))
            sharePrefRepository.saveAuthorizedUser(name, email, password)

            result.user?.let {
                database.collection("users").document(it.uid)
                    .set(User(it.uid, name, email))
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.failure(it))
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun autoLogin() {
        val loginData = sharePrefRepository.getLoginData()
        if (loginData != null) signIn(
            loginData.email,
            loginData.password
        )
    }

    override fun setImage(image: Uri) {

//        val fileRef = storage.reference.child("users/" + currentUser?.uid + "/profile.jpg")
//        fileRef.putFile(image).addOnFailureListener {
//            Log.i("hui", it.message.toString())
//        }.addOnSuccessListener {
//            Log.i("hui", "wiiiiiu")
//        }
        Log.i("hui", currentUser?.photoUrl.toString())
        currentUser?.updateProfile(
            UserProfileChangeRequest.Builder().setPhotoUri(image).build()
        )?.addOnSuccessListener {
            Log.i("hui", "wiiiiiu")
        }
    }

}