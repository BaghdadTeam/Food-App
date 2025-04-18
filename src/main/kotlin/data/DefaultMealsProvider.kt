package org.example.data

import model.Meal
import logic.MealsProvider
import org.example.logic.MealsRepository

class DefaultMealsProvider(
    private val repository: MealsRepository
) : MealsProvider {

    private val meals = repository.getAllMeals()

    override fun getMeals(): List<Meal> = meals
}