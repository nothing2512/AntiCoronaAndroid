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

@Dao
interface CasesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIndonesian(item: IndonesianEntity)

    @Query("SELECT * FROM `IndonesianEntity`")
    fun getIndonesian(): LiveData<IndonesianEntity>?

    @Query("DELETE FROM `indonesianentity`")
    fun deleteIndonesian()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvinces(items: ProvinceEntity)

    @Query("SELECT * FROM `ProvinceEntity`")
    fun getProvinces(): LiveData<List<ProvinceEntity>>?

    @Query("DELETE FROM `ProvinceEntity`")
    fun deleteProvinces()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGlobal(items: GlobalEntity)

    @Query("SELECT * FROM `GlobalEntity`")
    fun getGlobal(): LiveData<GlobalEntity>?

    @Query("DELETE FROM `GlobalEntity`")
    fun deleteGlobal()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(items: CountryEntity)

    @Query("SELECT * FROM `CountryEntity`")
    fun getCountries(): LiveData<List<CountryEntity>>?

    @Query("DELETE FROM `CountryEntity`")
    fun deleteCountries()
}