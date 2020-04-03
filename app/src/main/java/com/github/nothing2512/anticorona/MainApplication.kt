package com.github.nothing2512.anticorona

import android.app.Application
import com.github.nothing2512.anticorona.di.AppModule
import com.github.nothing2512.anticorona.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

@Suppress("unused")
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder().setDefaultFontPath(Constants.DEFAULT_FONT).build()
        )

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(AppModule.appModule)
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}