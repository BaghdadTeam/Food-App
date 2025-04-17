package org.example.logic

import model.Meal
import org.example.data.DefaultMealsProvider

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {


    fun getQuickHealthyMeals(count: Int): List<Meal> {
        return mealsProvider.getMeals()
            .filter(::isQuickAndHasNutrition)
            .sortedBy(::healthScore)
            .take(count)
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("There is no more healthy meals")
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