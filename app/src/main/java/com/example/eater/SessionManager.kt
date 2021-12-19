package com.example.eater

import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {

        const val USER_TOKEN = "user_token"
        const val Emailid="user@gmail.com"
        const val Membersince="0000000000"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?=null) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String?{

        return prefs.getString(USER_TOKEN,null)
    }
    fun saveEmail(email:String?=null) {

        val editor = prefs.edit()
        editor.putString(Emailid, email)
        editor.apply()
    }
    fun fetchEmail(): String?{

        return prefs.getString(Emailid,null)
    }
    fun saveMember(memberSince:Long) {
        val editor = prefs.edit()
        editor.putLong(Membersince, memberSince)
        editor.apply()
    }
    fun fetchMember(): Long{

        return prefs.getLong(Membersince,8767887777)
    }
}