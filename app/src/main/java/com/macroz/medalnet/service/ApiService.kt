package com.macroz.medalnet.service

import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.data.User
import com.macroz.medalnet.dtos.LoginRequestDto
import com.macroz.medalnet.dtos.LoginResDTO
import com.macroz.medalnet.dtos.RegisterRequestDTO
import com.macroz.medalnet.dtos.RegisterResDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    /////////////////////////////////////////// MedalApi ///////////////////////////////////////////

    @GET("/medal/all")
    fun getAllMedals(
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @GET("/medal/my")
    fun getMyMedals(
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @GET("/medal/search-by-name/{query}")
    fun getMedalsByName(
        @Path("query") query: String,
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @GET("/medal/search-by-number/{number}")
    fun getMedalsByNumber(
        @Path("number") number: String,
        @Header("Authorization") bearerToken: String
    ): Call<List<Medal>>

    @POST("/medal/add")
    fun addMedal(
        @Body medal: Medal,
        @Header("Authorization") bearerToken: String
    ): Call<Medal>

    /////////////////////////////////////////// UserApi ////////////////////////////////////////////

    @POST("/auth/login/{emailOrUsername}")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto,
        @Path("emailOrUsername") emailOrUsername: String
    ): Response<LoginResDTO>

    @POST("/auth/register")
    suspend fun register(
        @Body registerRequestDTO: RegisterRequestDTO
    ): Response<RegisterResDTO>

    @GET("/user/profile")
    fun getUserProfile(
        @Header("Authorization") bearerToken: String
    ): Call<User>
}