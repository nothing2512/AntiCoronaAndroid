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

package com.github.nothing2512.anticorona.di

import com.github.nothing2512.anticorona.data.local.CoronaDatabase
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.dao.NewsDao
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.repositories.FaqsRepository
import com.github.nothing2512.anticorona.repositories.NewsRepository
import com.github.nothing2512.anticorona.ui.country.CountryViewModel
import com.github.nothing2512.anticorona.ui.faqs.FaqsViewModel
import com.github.nothing2512.anticorona.ui.home.HomeViewModel
import com.github.nothing2512.anticorona.ui.news.NewsViewModel
import com.github.nothing2512.anticorona.ui.province.ProvinceViewModel
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.provideDatabase
import com.github.nothing2512.anticorona.utils.provideServices
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * [AppModule] object
 * @author Robet Atiq Maulana Rifqi
 * module for injection
 */
object AppModule {

    /**
     * variable appModule
     * listing required module for injection
     * @see module
     */
    val appModule = module {

        /**
         * Providing remote services
         * @see provideServices
         */
        single { provideServices() }

        /**
         * Providing in app database
         * @see provideDatabase
         */
        single { provideDatabase(androidApplication()) }

        /**
         * Providing Application Executors
         * @see AppExecutors
         */
        single { AppExecutors() }

        /**
         * Providing Shared Preference
         * @see Preference
         */
        single { Preference(androidContext()) }

        /**
         * Providing Dao`s
         *
         * @see CasesDao
         * @see FaqsDao
         * @see NewsDao
         */
        single { get<CoronaDatabase>().casesDao() }
        single { get<CoronaDatabase>().faqsDao() }
        single { get<CoronaDatabase>().newsDao() }

        /**
         * Providing Repository
         *
         * @see CaseRepository
         * @see FaqsRepository
         * @see NewsRepository
         */
        single { CaseRepository(get(), get(), get()) }
        single { FaqsRepository(get(), get(), get()) }
        single { NewsRepository(get(), get(), get()) }

        /**
         * Providing ViewModel
         *
         * @see CountryViewModel
         * @see FaqsViewModel
         * @see HomeViewModel
         * @see NewsViewModel
         * @see ProvinceViewModel
         */
        viewModel { CountryViewModel(get()) }
        viewModel { FaqsViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { NewsViewModel(get()) }
        viewModel { ProvinceViewModel(get()) }
    }
}