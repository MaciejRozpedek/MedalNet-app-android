package com.macroz.medalnet.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.data.User
import com.macroz.medalnet.dtos.LoginRequestDto
import com.macroz.medalnet.dtos.LoginResDTO
import com.macroz.medalnet.dtos.RegisterRequestDTO
import com.macroz.medalnet.dtos.RegisterResDTO
import com.macroz.medalnet.prefs
import com.macroz.medalnet.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {

    private val apiService: ApiService// TODO("remove hardcoded token")
    private var token: String = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzNDU2QHBhc3MubmV0IiwidXNlcm5hbWUiOiJ1c2VyMTIzNDU2IiwiZXhwIjoxOTM4ODk2MjQ4fQ.WW8ZqKJxX-g8tOG_4rkFeTJARz-Dm5nM08gXo2c-0gs"

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

    //////////////////////////////////////////// MEDALS ////////////////////////////////////////////

    fun getAllMedals(): LiveData<List<Medal>> {
        val medals = MutableLiveData<List<Medal>>()

        val call = apiService.getAllMedals("Bearer $token")
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

    fun getMyMedals(): LiveData<List<Medal>> {
        val medals = MutableLiveData<List<Medal>>()

        val call = apiService.getMyMedals("Bearer $token")
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

        val call = apiService.getMedalsByName(query, "Bearer $token")
        call.enqueue(object : Callback<List<Medal>> {
            override fun onResponse(call: Call<List<Medal>>, response: Response<List<Medal>>) {
                if (response.isSuccessful) {
                    medals.value = response.body()
                    Log.d("API_SUCCESS", "Request was successful: ${response.body()}")
                }  else {
                    medals.value = listOf(Medal(-1, null, null, null, null, null, null, null, -1))
                    val statusCode = response.code()
                    Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Medal>>, t: Throwable) {
                medals.value = listOf(Medal(-1, null, null, null, null, null, null, null, -1))
                Log.e("API_FAILURE", "Request failed (no response): ${t.message}")
            }
        })

        return medals
    }

    fun getMedalsByNumber(query: String): LiveData<List<Medal>> {
        val medals = MutableLiveData<List<Medal>>()

        val call = apiService.getMedalsByNumber(query, "Bearer $token")
        call.enqueue(object : Callback<List<Medal>> {
            override fun onResponse(call: Call<List<Medal>>, response: Response<List<Medal>>) {
                if (response.isSuccessful) {
                    medals.value = response.body()
                    Log.d("API_SUCCESS", "Request was successful: ${response.body()}")
                } else {
                    medals.value = listOf(Medal(-1, null, null, null, null, null, null, null, -1))
                    val statusCode = response.code()
                    Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Medal>>, t: Throwable) {
                medals.value = listOf(Medal(-1, null, null, null, null, null, null, null, -1))
                Log.e("API_FAILURE", "Request failed (no response): ${t.message}")
            }
        })

        return medals
    }

    interface AddMedalCallback {
        fun onSuccess()
        fun onFailure(message: String)
    }

    fun addMedal(medal: Medal, callback: AddMedalCallback) {
        val call = apiService.addMedal(medal, "Bearer $token")

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

    fun updateMedal(medal: Medal, callback: AddMedalCallback) {
        val call = apiService.updateMedal(medal, "Bearer $token")

        call.enqueue(object : Callback<Medal> {
            override fun onResponse(call: Call<Medal>, response: Response<Medal>) {
                if (response.isSuccessful) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Failed to update medal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Medal>, t: Throwable) {
                callback.onFailure("Request failed: ${t.message}")
            }
        })
    }

    //////////////////////////////////////////// USERS ////////////////////////////////////////////

    suspend fun login(emailOrUsername: String, emailOrUsernameValue: String, password: String): Boolean {
        val loginRequestDto = LoginRequestDto(emailOrUsernameValue, emailOrUsernameValue, password)

        return try {
            val response: Response<LoginResDTO> = apiService.login(loginRequestDto, emailOrUsername)
            if (response.isSuccessful) {
                token = response.body()?.token.orEmpty()
                prefs.saveToken(token)
                Log.d("API_SUCCESS", "Request was successful: $token")
                true // Login was successful
            } else {
                val statusCode = response.code()
                Log.e("API_ERROR", "HTTP Status Code: $statusCode")
                val errorBody = response.errorBody()?.string()
                Log.e("API_ERROR", "Error Body: $errorBody")
                false // Login failed
            }
        } catch (e: Exception) {
            Log.e("API_FAILURE", "Request failed: ${e.message}")
            false // Login failed
        }
    }

    suspend fun register(email: String, username: String, password: String): Boolean {
        val registerRequestDTO = RegisterRequestDTO(username, email, password)

        return try {
            val response: Response<RegisterResDTO> = apiService.register(registerRequestDTO)
            if (response.isSuccessful) {
                token = response.body()?.token.orEmpty()
                prefs.saveToken(token)
                Log.d("API_SUCCESS", "Request was successful: $token")
                true // Register was successful
            } else {
                val statusCode = response.code()
                Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                val errorBody = response.errorBody()?.string()
                Log.e("API_ERROR", "Error Body: $errorBody")
                false // Register failed
            }
        } catch (e: Exception) {
            Log.e("API_FAILURE", "Request failed: ${e.message}")
            false // Register failed
        }
    }

    fun getUserProfile(): LiveData<User> {
        val user = MutableLiveData<User>()

        val call = apiService.getUserProfile("Bearer $token")
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user.value = response.body()
                    Log.d("API_SUCCESS", "Request was successful: ${response.body()}")
                } else {
                    val statusCode = response.code()
                    Log.e("API_ERROR", "HTTP Status Code: $statusCode")

                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API_FAILURE", "Request failed (no response): ${t.message}")
            }
        })

        return user
    }

    fun saveEmail(email: String) {
        prefs.saveEmail(email)
    }

    fun saveUsername(username: String) {
        prefs.saveUsername(username)
    }

    fun savePassword(password: String) {
        prefs.savePassword(password)
    }

    fun getEmail(): String {
        return prefs.getEmail().orEmpty()
    }

    fun getUsername(): String {
        return prefs.getUsername().orEmpty()
    }

    fun getPassword(): String {
        return prefs.getPassword().orEmpty()
    }

    fun saveToken(token: String) {
        prefs.saveToken(token)
    }

    fun getToken(): String {
        return prefs.getToken().orEmpty()
    }
}