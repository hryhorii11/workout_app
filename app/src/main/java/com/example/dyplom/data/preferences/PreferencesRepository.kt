package com.example.dyplom.data.preferences

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.dyplom.data.user.model.LoginData

interface PreferencesRepository {

    fun saveAuthorizedUser(name:String,email: String,password:String)
    fun deleteAuthorizedUserData()
    fun getLoginData():LoginData?
}