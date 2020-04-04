package com.github.nothing2512.anticorona.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.nothing2512.anticorona.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: NewsEntity)

    @Query("SELECT * FROM `NewsEntity`")
    fun getNews(): LiveData<List<NewsEntity>>?

    @Query("DELETE FROM `NewsEntity`")
    fun delete()
}