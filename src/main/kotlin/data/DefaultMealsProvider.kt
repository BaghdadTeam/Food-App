package org.example.data

import model.Meal
import org.example.logic.MealsProvider
import org.example.logic.MealsRepository
import org.koin.mp.KoinPlatform.getKoin

class DefaultMealsProvider(
    private val repository: MealsRepository
) : MealsProvider {

    override fun getMeals(): List<Meal> = repository.getAllMeals()
}