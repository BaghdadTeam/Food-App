package org.example.di

import data.CsvReader
import data.RecordParser
import org.example.data.CsvMealsRepository
import org.example.data.DefaultMealsProvider
import logic.MealsProvider
import org.example.logic.MealsRepository
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single<File> { File("food.csv") }
    single { RecordParser() }
    single { CsvReader(get()) }

    single<MealsRepository> { CsvMealsRepository(get(), get()) }

    single<MealsProvider> { DefaultMealsProvider(get()) }
}