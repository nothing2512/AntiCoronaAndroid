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

import android.content.Context
import androidx.room.Room
import com.github.nothing2512.anticorona.data.local.CoronaDatabase
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.dao.NewsDao

/**
 * [DatabaseService] class
 * @author Robet Atiq Maulana Rifqi
 */
class DatabaseService {

    /**
     * Deleting data in database
     * @param context
     */
    fun delete(context: Context, all: Boolean = false) {

        /**
         * Providing Database
         * @see CoronaDatabase
         */
        val db = provideDatabase(context.applicationContext)
        val casesDao = db.casesDao()
        val newsDao = db.newsDao()
        val faqsDao = db.faqsDao()

        /**
         * Deleting data in database
         * @see CasesDao
         * @see NewsDao
         * @see FaqsDao
         */
        casesDao.deleteIndonesian()
        casesDao.deleteGlobal()
        casesDao.deleteProvinces()
        casesDao.deleteCountries()

        newsDao.delete()
        if (all) faqsDao.delete()
    }

    /**
     * function provideDatabase
     * providing in app database
     *
     * @param context
     *
     * @see Context
     * @see Room.databaseBuilder
     *
     * @see CoronaDatabase
     *
     * @return CoronaDatabase
     */
    private fun provideDatabase(context: Context) =
        Room
            .databaseBuilder(
                context,
                CoronaDatabase::class.java,
                "AntiCorona.db"
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
}