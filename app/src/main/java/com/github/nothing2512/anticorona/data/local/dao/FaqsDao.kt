package com.github.nothing2512.anticorona.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.nothing2512.anticorona.data.local.entity.FaqsEntity

@Dao
interface FaqsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: FaqsEntity)

    @Query("SELECT * FROM `FaqsEntity`")
    fun getFaqs(): LiveData<List<FaqsEntity>>?

    @Query("DELETE FROM `FaqsEntity`")
    fun delete()
}