package org.example.logic

import model.Meal
import org.example.data.MealsProvider

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {


    fun getQuickHealthyMeals(count: Int): List<Meal> {
        return try {
            mealsProvider.meals
                .filter(::isQuickAndHasNutrition)
                .sortedBy(::healthScore)
                .take(count)
        } catch (e: Exception) {
            println("Failed to filter meals ${e.message}")
            emptyList()
        }
    }

    private fun isQuickAndHasNutrition(meal: Meal): Boolean {
        return meal.preparationTime != null &&
                meal.preparationTime <= 15 &&
                meal.nutrition != null
    }

    private fun healthScore(meal: Meal): Double {
        val nutrition = meal.nutrition!!
        return (nutrition.totalFat ?: 0.0) +
                (nutrition.saturatedFat ?: 0.0) +
                (nutrition.carbohydrates ?: 0.0)
    }
}