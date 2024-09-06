package com.macroz.medalnet

import android.app.Application
import android.widget.Toast
import com.macroz.medalnet.repository.DataRepository
import com.macroz.medalnet.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//val userService: UserService by lazy {
//    MedalNetApplication.userService!!
//}

val token: String = "Bearer testToken123"
class MedalNetApplication: Application() {

    companion object {
//        var userService: UserService? = UserService()
        lateinit var instance: MedalNetApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    val repository by lazy { DataRepository() }
    val applicationScope = CoroutineScope(SupervisorJob())
}