package com.example.dyplom.data.preferences

import android.content.Context
import com.example.dyplom.data.user.model.LoginData
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val context: Context
) : PreferencesRepository {

    private val sharedPref = context.getSharedPreferences(
        "sharedPref", Context.MODE_PRIVATE
    )

    private val userNameKey="userName"
    private val passwordKey="password"
    private val emailKey="email"
    private val isUserAuthorizedKey="getAuth"
    override fun saveAuthorizedUser(name: String, email: String, password: String) {
        with(sharedPref.edit()) {
            putString(userNameKey,name)
            putString(passwordKey,password)
            putString(emailKey,email)
            putBoolean(isUserAuthorizedKey,true)
            apply()
        }
    }

    override fun deleteAuthorizedUserData() {
        with(sharedPref.edit()) {
            putBoolean(isUserAuthorizedKey,false)
            apply()
        }
    }

    override fun getLoginData(): LoginData? {
        return if (!sharedPref.getBoolean(isUserAuthorizedKey, false))
            null
        else {
            LoginData(
                sharedPref.getString(emailKey,"")!!,
                sharedPref.getString(passwordKey,"")!!
            )
        }
    }


}