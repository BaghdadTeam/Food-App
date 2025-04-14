package org.example.data

import org.example.logic.MealsRepository
import org.koin.mp.KoinPlatform.getKoin

object MealsProvider {
    val meals = getKoin().get<MealsRepository>().getAllMeals()
}