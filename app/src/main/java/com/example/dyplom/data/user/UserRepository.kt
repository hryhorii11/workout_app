package com.example.dyplom.data.user

import android.net.Uri
import com.example.dyplom.data.UIState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: FirebaseUser?
    fun signIn(email: String, password: String): Flow<Result<Unit>>
    fun signUp(name: String, email: String, password: String): Flow<Result<Unit>>
    fun logout()
    fun autoLogin()
    fun setImage(image: Uri)
}