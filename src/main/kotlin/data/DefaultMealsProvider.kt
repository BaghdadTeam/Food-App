package org.example.data

import logic.MealsProvider
import model.Meal
import org.example.logic.MealsRepository

class DefaultMealsProvider(
    repository: MealsRepository,
) : MealsProvider {

    private val meals: List<Meal> = try {
        repository.getAllMeals()
    } catch (e: Exception) {
        emptyList()
    }

    override fun getMeals(): List<Meal> = meals
}