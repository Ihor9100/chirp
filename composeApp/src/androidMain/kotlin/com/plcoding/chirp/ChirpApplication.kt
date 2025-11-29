package com.plcoding.chirp

import android.app.Application
import com.plcoding.chirp.di.initKoin

class ChirpApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    initKoin()
  }
}