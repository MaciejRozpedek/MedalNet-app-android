package com.macroz.medalnet.utils

import android.util.Base64
import org.json.JSONObject
import java.util.Date

object JwtUtils {
    fun decodeJwt(token: String): JSONObject? {
        val parts = token.split(".")
        if (parts.size != 3) return null

        val payload = parts[1]
        val decodedPayload = String(Base64.decode(payload, Base64.URL_SAFE))

        return try {
            JSONObject(decodedPayload)
        } catch (e: Exception) {
            null
        }
    }

    fun isTokenExpired(token: String): Boolean {
        val json: JSONObject = decodeJwt(token) ?: return true

        val exp = json.optLong("exp", -1)
        if (exp == -1L) return true

        val expirationDate = Date(exp * 1000)
        val currentDate = Date()

        return currentDate.after(expirationDate)
    }
}