package org.example.logic

import model.Meal
import org.example.data.MealsProvider

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {

    /**
     * Returns a list of [count] meals that are quick to prepare and have low fat, saturated fat, and carbohydrate content.
     */

    fun getQuickHealthyMeals(count: Int): List<Meal> {
        return try {


            mealsProvider.meals
                .filter { meal ->
                    meal.timeToCock != null &&
                            meal.timeToCock <= 15 &&
                            meal.nutrition != null
                }
                .sortedBy { meal ->
                    val nutrition = meal.nutrition
                    (nutrition?.totalFat ?: 0.0) +
                            (nutrition?.saturatedFat ?: 0.0) +
                            (nutrition?.carbohydrates ?: 0.0)
                }
                .take(count)
        } catch (e: Exception) {
            println("Failed to filter meals ${e.message}")
            emptyList()
        }
    }



}