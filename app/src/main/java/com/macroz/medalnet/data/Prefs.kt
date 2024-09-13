package com.macroz.medalnet.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class Prefs(context: Context) {
    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val preferences: SharedPreferences = EncryptedSharedPreferences.create(
        context, "DATA", masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val editor = preferences.edit()

    fun saveEmail(email: String) {
        editor.putString("email", email)
        editor.commit()
    }

    fun savePassword(password: String) {
        editor.putString("password", password)
        editor.commit()
    }

    fun saveUsername(username: String) {
        editor.putString("username", username)
        editor.commit()
    }

    fun getEmail(): String? {
        return preferences.getString("email", null)
    }

    fun getPassword(): String? {
        return preferences.getString("password", null)
    }

    fun getUsername(): String? {
        return preferences.getString("username", null)
    }

    fun saveToken(token: String) {
        editor.putString("token", token)
        editor.commit()
    }

    fun getToken(): String? {
        return preferences.getString("token", null)
    }
}