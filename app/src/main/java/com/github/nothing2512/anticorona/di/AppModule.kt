package com.github.nothing2512.anticorona.di

import com.github.nothing2512.anticorona.BuildConfig
import com.github.nothing2512.anticorona.data.Services
import com.github.nothing2512.anticorona.data.adapter.CallAdapterFactory
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.repositories.FaqsRepository
import com.github.nothing2512.anticorona.repositories.NewsRepository
import com.github.nothing2512.anticorona.ui.country.CountryViewModel
import com.github.nothing2512.anticorona.ui.faqs.FaqsViewModel
import com.github.nothing2512.anticorona.ui.home.HomeViewModel
import com.github.nothing2512.anticorona.ui.news.NewsViewModel
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.Preference
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    val appModule = module {

        single { provideServices() }
        single { AppExecutors() }
        single { Preference(androidContext()) }

        single { CaseRepository(get(), get()) }
        single { FaqsRepository(get(), get()) }
        single { NewsRepository(get(), get()) }

        viewModel { CountryViewModel(get()) }
        viewModel { FaqsViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { NewsViewModel(get()) }
    }

    private fun provideServices() = Retrofit.Builder()
        .addCallAdapterFactory(CallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(Services::class.java)
}