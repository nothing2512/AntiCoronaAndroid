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

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * [DatabaseReceiver] class
 * @author Robet Atiq Maulana Rifqi
 * @see BroadcastReceiver
 */
class DatabaseReceiver : BroadcastReceiver() {

    /**
     * triggered function when receiving data
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->

            /**
             * Deleting Database
             * @see DatabaseService
             */
            val service = DatabaseService()
            service.delete(ctx)
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
            val intent = Intent(context, DatabaseReceiver::class.java)
            return PendingIntent.getBroadcast(
                context,
                2512,
                intent,
                0
            )
        }

        /**
         * Check pending intent is registered or not
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun isActive(context: Context): Boolean {
            val intent = Intent(context, DatabaseReceiver::class.java)
            return (PendingIntent.getBroadcast(
                context,
                2512,
                intent,
                PendingIntent.FLAG_NO_CREATE
            ) != null)
        }
    }

}