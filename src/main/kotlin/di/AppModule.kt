package org.example.di

import data.CsvReader
import data.RecordParser
import org.example.data.CsvMealsRepository
import org.example.data.DefaultMealsProvider
import logic.MealsProvider
import org.example.logic.MealsRepository
import org.example.logic.SearchAlgorithm
import org.example.logic.search.KMPSearchAlgorithm
import org.example.presentation.*

import org.koin.dsl.module
import java.io.File

val appModule = module {
    single<File> { File("food.csv") }
    single { RecordParser() }
    single { CsvReader(get()) }

    single<SearchAlgorithm> { KMPSearchAlgorithm() }

    single<MealsRepository> { CsvMealsRepository(get(), get()) }

    single<MealsProvider> { DefaultMealsProvider(get()) }
    single<Reader> { ReaderImpl() }
    single<Viewer> { ViewerImpl() }

}
