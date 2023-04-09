package com.ibaevzz.rwp

import android.app.Application
import com.ibaevzz.rwp.di.DaggerAppComponent

const val WEB_CLIENT = "51655664545-kd0jtc73m9qrtnd6joesbdqelmrjno4p.apps.googleusercontent.com"

class App: Application() {
    val appComponent = DaggerAppComponent.builder().context(this).build()
}