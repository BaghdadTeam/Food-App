package org.example

import data.CsvReader
import data.RecordParser
import model.Meal
import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.MealsRepository
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import java.io.File

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }
    val meals: List<Meal> = getKoin().get<MealsRepository>().getAllMeals()

    println("Total meals: ${meals.size}")
}