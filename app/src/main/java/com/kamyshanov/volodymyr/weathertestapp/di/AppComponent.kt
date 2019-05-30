package com.kamyshanov.volodymyr.weathertestapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
  single { androidContext().resources }
}

val appComponent = listOf(appModule, networkModule, dataModule, domainModule, presentationModule)
