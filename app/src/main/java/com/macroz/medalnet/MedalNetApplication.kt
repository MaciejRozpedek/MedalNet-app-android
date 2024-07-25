package com.macroz.medalnet

import android.app.Application
import android.widget.Toast
import com.macroz.medalnet.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//val userService: UserService by lazy {
//    MedalNetApplication.userService!!
//}

//var token: String = ""

class MedalNetApplication: Application() {

    companion object {
//        var userService: UserService? = UserService()
        lateinit var instance: MedalNetApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
//        userService = UserService(applicationContext)
//        runBlocking {
//            launch {
//              val loginResDTO = userService!!.login("email",
//                  "user123456@pass.net", "p@sswOrd")
//println(loginResDTO?.token)
//                Toast.makeText(applicationContext, loginResDTO?.token, Toast.LENGTH_LONG).show()
//            }
//        }
    }

    val applicationScope = CoroutineScope(SupervisorJob())
}