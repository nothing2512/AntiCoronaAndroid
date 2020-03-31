package com.github.nothing2512.anticorona

import android.app.Application
import com.github.nothing2512.anticorona.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(AppModule.appModule)
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}