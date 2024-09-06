package com.macroz.medalnet.data

data class Medal(
    val id: Long,
    val number: String?,
    val name: String?,
    val surname: String?,
    val rank: String?,
    val unit: String?,
    val year: Long?,
    val notes: String?,
    val userId: Long    // Id of user, who added this medal
)