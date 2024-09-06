package com.macroz.medalnet.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.service.ApiService
import com.macroz.medalnet.token
import okhttp3.OkHttpClient
import okhttp3.internal.platform.android.AndroidLogHandler.setLevel
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {

    private val apiService: ApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://medalnet-server.up.railway.app")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getAllMedals(): LiveData<List<Medal>> {
        val medals = MutableLiveData<List<Medal>>()

        val call = apiService.getAllMedals(token)
        call.enqueue(object : Callback<List<Medal>> {
            override fun onResponse(call: Call<List<Medal>>, response: Response<List<Medal>>) {
                if (response.isSuccessful) {
                    medals.value = response.body()
                    Log.d("API_SUCCESS", "Request was successful: ${response.body()}")
                } else {
                    val statusCode = response.code()
                    Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Medal>>, t: Throwable) {
                Log.e("API_FAILURE", "Request failed (no response): ${t.message}")
            }
        })

        return medals
    }

    fun getMedalsByName(query: String): LiveData<List<Medal>> {
        val medals = MutableLiveData<List<Medal>>()

        val call = apiService.getMedalsByName(query, token)
        call.enqueue(object : Callback<List<Medal>> {
            override fun onResponse(call: Call<List<Medal>>, response: Response<List<Medal>>) {
                if (response.isSuccessful) {
                    medals.value = response.body()
                    Log.d("API_SUCCESS", "Request was successful: ${response.body()}")
                }  else {
                    val statusCode = response.code()
                    Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Medal>>, t: Throwable) {
                Log.e("API_FAILURE", "Request failed (no response): ${t.message}")
            }
        })

        return medals
    }

    interface AddMedalCallback {
        fun onSuccess()
        fun onFailure(message: String)
    }

    fun addMedal(medal: Medal, bearerToken: String, callback: AddMedalCallback) {
        val call = apiService.addMedal(medal, "$bearerToken")

        call.enqueue(object : Callback<Medal> {
            override fun onResponse(call: Call<Medal>, response: Response<Medal>) {
                if (response.isSuccessful) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Failed to add medal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Medal>, t: Throwable) {
                callback.onFailure("Request failed: ${t.message}")
            }
        })
    }
}