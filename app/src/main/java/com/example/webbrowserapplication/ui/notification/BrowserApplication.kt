package com.example.webbrowserapplication.ui.notification


import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner

class BrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(
                AppLifecycleTracker
            )
    }
}