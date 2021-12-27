package com.example.clevertapexampleapplication.application

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI

class MyApplication :Application() {


    companion object{
        @JvmStatic
        var cleverTapDefaultInstance: CleverTapAPI? = null
    }

    override fun onCreate() {
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE)
        Log.i("TAG", "onCreate: 1")
        ActivityLifecycleCallback.register(this);
        Log.i("TAG", "onCreate: 2")
        super.onCreate()
        Log.i("TAG", "onCreate: 3")
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
        Log.i("TAG", "onCreate: 4")
        cleverTapDefaultInstance?.enablePersonalization();
        Log.i("TAG", "onCreate: 5")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CleverTapAPI.createNotificationChannel(baseContext,"promotion","promotion","promotion",
                NotificationManager.IMPORTANCE_MAX,true)
        }
    }

    fun getCleverTapInstance(): CleverTapAPI? {
        return cleverTapDefaultInstance
    }


}