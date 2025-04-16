package org.example.logic

import model.Meal
import org.example.data.DefaultMealsProvider


class IraqiMealsIdentifierUseCase(
    private val mealsProvider: MealsProvider
) {

    fun getIraqiMeals(): List<Meal> {
        return mealsProvider.getMeals().filter(::isIraqiMeal)
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
        val iraqiTag = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true
        val iraqDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return iraqiTag || iraqDesc
    }
}