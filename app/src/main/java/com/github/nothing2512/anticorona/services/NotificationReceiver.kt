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

package com.github.nothing2512.anticorona.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.nothing2512.anticorona.data.remote.ApiSuccessResponse
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.NotificationSender
import com.github.nothing2512.anticorona.utils.provideServices
import java.util.*

/**
 * [NotificationReceiver] class
 * @author Robet Atiq Maulana Rifqi
 * @see BroadcastReceiver
 */
class NotificationReceiver : BroadcastReceiver() {

    /**
     * Triggered function when receiving data
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->

            /**
             * providing services and executors
             * @see provideServices
             * @see AppExecutors
             */
            val services = provideServices()
            val appExecutors = AppExecutors()

            /**
             * executing main thread for background process
             * @see AppExecutors.mainThread
             */
            appExecutors.mainThread.execute {

                /**
                 * Get Lang code for fetch data param
                 * getting news data from remote service
                 * @see Locale
                 * @see Services.getNews
                 */
                val lang = if (Locale.getDefault().country == Constants.LANG_ID) "in" else "eng"
                services.getNews(lang).observeForever {
                    if (it is ApiSuccessResponse) {

                        /**
                         * Send notification
                         * @see NotificationSender
                         */
                        val notification = NotificationSender(ctx)
                        notification.send(it.body[0])
                    }
                }
            }
        }
    }

    companion object {

        /**
         * get receiver pending intent instance
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getInstance(context: Context): PendingIntent {
            val intent = Intent(context, NotificationReceiver::class.java)
            return PendingIntent.getBroadcast(
                context,
                2506,
                intent,
                0
            )
        }

        /**
         * start alarm
         * @see AlarmManager
         * @see Calendar
         */
        @JvmStatic
        fun start(context: Context) {
            val instance = getInstance(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                instance
            )
        }

        /**
         * canceling alarm
         * @see AlarmManager
         */
        @JvmStatic
        fun cancel(context: Context) {
            if (isActive(context)) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(getInstance(context))
            }
        }

        /**
         * Check pending intent is registered or not
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun isActive(context: Context): Boolean {
            val intent = Intent(context, NotificationReceiver::class.java)
            return (PendingIntent.getBroadcast(
                context,
                2506,
                intent,
                PendingIntent.FLAG_NO_CREATE
            ) != null)
        }
    }
}