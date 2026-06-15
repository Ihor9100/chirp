package com.plcoding.chirp

import android.app.Application
import com.plcoding.chirp.di.initKoin
import org.koin.android.ext.koin.androidContext

class ChirpApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    initKoin {
      androidContext(this@ChirpApplication)
    }
  }
}