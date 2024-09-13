package com.macroz.medalnet

import android.app.Application
import com.macroz.medalnet.data.Prefs
import com.macroz.medalnet.repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val prefs: Prefs by lazy {
    MedalNetApplication.prefs!!
}

class MedalNetApplication: Application() {

    companion object {
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