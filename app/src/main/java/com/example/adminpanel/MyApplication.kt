package com.example.adminpanel

import android.app.Application
import com.facebook.FacebookSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.setClientToken("56789") // Replace with your actual client token
        FacebookSdk.sdkInitialize(applicationContext)
    }
}