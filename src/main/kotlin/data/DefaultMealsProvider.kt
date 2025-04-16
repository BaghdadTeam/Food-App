package org.example.data

import org.example.logic.MealsRepository
import org.koin.mp.KoinPlatform.getKoin

/**
 * A singleton object to get all the meals from the data source
 * and do minimal read operations on the file
 */
object MealsProvider {
    val meals = getKoin().get<MealsRepository>().getAllMeals()
}