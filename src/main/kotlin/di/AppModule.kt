package org.example.di

import data.CsvReader
import data.RecordParser
import org.example.data.CsvMealsRepository
import org.example.data.MealsProvider
import org.example.logic.MealsRepository
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { RecordParser() }
    single { CsvReader(get()) }
    single { MealsProvider }

    single<MealsRepository> { CsvMealsRepository(get(), get()) }
}