package com.example.zcontacts

import android.app.Application
import android.util.Log
import com.example.zcontacts.di.AppModule
import com.example.zcontacts.di.DaggerMainComponent
import com.example.zcontacts.di.MainComponent
import javax.inject.Inject


class MyApplication @Inject constructor() : Application() {
    companion object {
        lateinit var mainComponent: MainComponent
    }


    override fun onCreate() {
        Log.i("hello", ": init called")
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .build()
        Log.i("hello", ": mainComponent $mainComponent ")
        super.onCreate()

    }

    fun component(): MainComponent {

        return mainComponent

    }
}