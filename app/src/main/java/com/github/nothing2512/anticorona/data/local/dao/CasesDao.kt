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
import com.github.nothing2512.anticorona.data.local.entity.CountryEntity
import com.github.nothing2512.anticorona.data.local.entity.GlobalEntity
import com.github.nothing2512.anticorona.data.local.entity.IndonesianEntity
import com.github.nothing2512.anticorona.data.local.entity.ProvinceEntity

/**
 * [CasesDao] Interface
 * @author Robet Atiq Maulana Rifqi
 * @see Dao
 */
@Dao
interface CasesDao {

    /**
     * Function insertIndonesian
     * Inserting Indonesian Data
     *
     * @param item
     *
     * @see Insert
     * @see OnConflictStrategy.REPLACE
     * @see IndonesianEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIndonesian(item: IndonesianEntity)

    /**
     * Function getIndonesian
     * Getting single data from indonesian entity
     *
     * @see Query
     * @see IndonesianEntity
     *
     * @return LiveData<IndonesianEntity>
     */
    @Query("SELECT * FROM `IndonesianEntity`")
    fun getIndonesian(): LiveData<IndonesianEntity>?

    /**
     * Function deleteIndonesian
     * delete indonesian data
     *
     * @see Query
     * @see IndonesianEntity
     */
    @Query("DELETE FROM `indonesianentity`")
    fun deleteIndonesian()

    /**
     * Function insertProvinces
     * inserting province data
     *
     * @param items
     *
     * @see Insert
     * @see OnConflictStrategy.REPLACE
     * @see IndonesianEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvinces(items: ProvinceEntity)

    /**
     * Function getProvinces
     * getting provinces data
     *
     * @see Query
     * @see ProvinceEntity
     *
     * @return LiveData<List<ProvinceEntity>>
     */
    @Query("SELECT * FROM `ProvinceEntity`")
    fun getProvinces(): LiveData<List<ProvinceEntity>>?

    /**
     * Function deleteProvinces
     * Deleting province data
     *
     * @see Query
     * @see ProvinceEntity
     */
    @Query("DELETE FROM `ProvinceEntity`")
    fun deleteProvinces()

    /**
     * Function insertGlobal
     * getting global corona cases
     *
     * @param items
     *
     * @see Insert
     * @see OnConflictStrategy.REPLACE
     * @see GlobalEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGlobal(items: GlobalEntity)

    /**
     * Function getGlobal
     * getting global corona cases
     *
     * @see Query
     * @see GlobalEntity
     *
     * @return LiveData<GlobalEntity>
     */
    @Query("SELECT * FROM `GlobalEntity`")
    fun getGlobal(): LiveData<GlobalEntity>?

    /**
     * Function deleteGlobal
     * deleting global corona cases data
     *
     * @see Query
     * @see GlobalEntity
     */
    @Query("DELETE FROM `GlobalEntity`")
    fun deleteGlobal()

    /**
     * Function insertCountries
     * Inserting countries data
     *
     * @param items
     *
     * @see Insert
     * @see OnConflictStrategy.REPLACE
     * @see CountryEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(items: CountryEntity)

    /**
     * Function
     * Getting Countries Data
     *
     * @see Query
     * @see CountryEntity
     *
     * @return LiveData<List<CountryEntity>>
     */
    @Query("SELECT * FROM `CountryEntity`")
    fun getCountries(): LiveData<List<CountryEntity>>?

    /**
     * Function deleteCountries
     * Deleting Countries Data
     *
     * @see Query
     * @see CountryEntity
     */
    @Query("DELETE FROM `CountryEntity`")
    fun deleteCountries()
}