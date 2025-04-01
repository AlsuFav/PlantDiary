package ru.fav.plantdiary

import android.app.Application
import ru.fav.plantdiary.di.ServiceLocator

class App : Application() {

    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator.initDataLayerDependencies(ctx = this)
    }
}