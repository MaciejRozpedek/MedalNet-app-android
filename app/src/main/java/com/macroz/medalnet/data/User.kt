package com.macroz.medalnet.data

data class User(
    val id: Long,
    val username: String,
    val password: String?,
    val email: String,
    val base64profilePicture: String?
)