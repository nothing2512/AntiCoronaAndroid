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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.github.nothing2512.anticorona.data.local.CoronaDatabase
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.dao.NewsDao

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

}