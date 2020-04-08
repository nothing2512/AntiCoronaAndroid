/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nothing2512.anticorona

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.github.nothing2512.anticorona.di.AppModule
import com.github.nothing2512.anticorona.services.DatabaseReceiver
import com.github.nothing2512.anticorona.services.LanguageReceiver
import com.github.nothing2512.anticorona.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * [MainApplication] Class
 * @author Robet Atiq Maulana Rifqi
 */
@Suppress("unused")
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Initialize Default Font
         * @see CalligraphyConfig.initDefault
         */
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder().setDefaultFontPath(Constants.DEFAULT_FONT).build()
        )

        /**
         * Starting Koin Depedency Injection
         * @see startKoin
         * @see AppModule.appModule
         */
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(AppModule.appModule)
        }

        /**
         * Registering Database Service
         *
         * @see DatabaseReceiver.isActive
         * @see DatabaseReceiver.getInstance
         * @see AlarmManager
         */
        if (!DatabaseReceiver.isActive(applicationContext)) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                Constants.BROADCAST_LATENCY,
                DatabaseReceiver.getInstance(applicationContext)
            )
        }


        /**
         * Registering Language Service
         *
         * @see IntentFilter
         * @see LanguageReceiver
         */
        val intentFilter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(LanguageReceiver(), intentFilter)

        /**
         * Using Timber for debugging
         * @see Timber
         */
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}