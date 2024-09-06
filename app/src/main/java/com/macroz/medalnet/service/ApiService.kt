package com.macroz.medalnet.service

import com.macroz.medalnet.data.Medal
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/medal/all")
    fun getAllMedals(
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @GET("/medal/search-by-name/{query}")
    fun getMedalsByName(
        @Path("query") query: String,
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @POST("/medal/add")
    fun addMedal(
        @Body medal: Medal,
        @Header("Authorization") bearerToken: String
    ): Call<Medal>
}