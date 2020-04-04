package com.github.nothing2512.anticorona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.dao.NewsDao
import com.github.nothing2512.anticorona.data.local.entity.*

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

    abstract fun casesDao(): CasesDao

    abstract fun newsDao(): NewsDao

    abstract fun faqsDao(): FaqsDao
}