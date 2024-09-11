package com.macroz.medalnet

import android.app.Application
import com.macroz.medalnet.data.Prefs
import com.macroz.medalnet.repository.DataRepository
import com.macroz.medalnet.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val userService: UserService by lazy {
    MedalNetApplication.userService!!
}

val prefs: Prefs by lazy {
    MedalNetApplication.prefs!!
}
// TODO("add logging")
var token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzNDU2QHBhc3MubmV0IiwidXNlcm5hbWUiOiJ1c2VyMTIzNDU2IiwiZXhwIjoxOTM4ODk2MjQ4fQ.WW8ZqKJxX-g8tOG_4rkFeTJARz-Dm5nM08gXo2c-0gs"

class MedalNetApplication: Application() {

    companion object {
//        var userService: UserService? = UserService()
        var prefs: Prefs? = null
        lateinit var instance: MedalNetApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)
    }

    val repository by lazy { DataRepository() }
    val applicationScope = CoroutineScope(SupervisorJob())
}