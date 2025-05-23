package org.example.logic.usecase.suggest

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class IraqiMealsIdentifierUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")

        return mealsProvider.getMeals()
            .filter(::isIraqiMeal)
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("There is no Iraqi Meals")

    }

    private fun isIraqiMeal(meal: Meal): Boolean {
        if (meal.tags.isNullOrEmpty() && meal.description.isNullOrBlank()) return false

        val inTags = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true

        val inDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return inTags || inDesc
    }
}