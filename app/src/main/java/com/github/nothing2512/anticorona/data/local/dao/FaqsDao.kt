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

package com.github.nothing2512.anticorona.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.nothing2512.anticorona.data.local.entity.FaqsEntity

/**
 * [FaqsDao] Interfaces
 * @author Robet Atiq Maulana Rifqi
 * @see Dao
 */
@Dao
interface FaqsDao {

    /**
     * function insert
     * inserting faqs data
     *
     * @param items
     *
     * @see Insert
     * @see OnConflictStrategy.REPLACE
     * @see FaqsEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: FaqsEntity)

    /**
     * function getFaqs
     * get faqs data
     *
     * @see Query
     * @see FaqsEntity
     *
     * @return LiveData<List<FaqsEntity>>
     */
    @Query("SELECT * FROM `FaqsEntity`")
    fun getFaqs(): LiveData<List<FaqsEntity>>?

    /**
     * function delete
     * deleting faqs data
     *
     * @see Query
     * @see FaqsEntity
     */
    @Query("DELETE FROM `FaqsEntity`")
    fun delete()
}