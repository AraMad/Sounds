package com.orynastarkina.sounds

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Oryna Starkina on 26.02.2019.
 */
class SoundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}