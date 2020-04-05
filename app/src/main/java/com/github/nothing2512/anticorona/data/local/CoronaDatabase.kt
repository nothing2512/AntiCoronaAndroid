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

package com.github.nothing2512.anticorona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.dao.NewsDao
import com.github.nothing2512.anticorona.data.local.entity.*

/**
 * [CoronaDatabase] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @see Database
 * @see CountryEntity
 * @see FaqsEntity
 * @see GlobalEntity
 * @see IndonesianEntity
 * @see NewsEntity
 * @see ProvinceEntity
 * @see RoomDatabase
 */
@Database(
    entities = [
        CountryEntity::class,
        FaqsEntity::class,
        GlobalEntity::class,
        IndonesianEntity::class,
        NewsEntity::class,
        ProvinceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CoronaDatabase : RoomDatabase() {

    /**
     * function casesDao
     * getting CasesDao
     *
     * @see CasesDao
     * @return CasesDao
     */
    abstract fun casesDao(): CasesDao

    /**
     * function newsDao
     * getting NewsDao
     *
     * @see NewsDao
     * @return NewsDao
     */
    abstract fun newsDao(): NewsDao

    /**
     * function faqsDao
     * getting FaqsDao
     *
     * @see FaqsDao
     * @return FaqsDao
     */
    abstract fun faqsDao(): FaqsDao
}