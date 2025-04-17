package logic.use_case

import model.Meal
import org.example.data.MealsProvider

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {

    /**
     * Returns a list of [count] meals that are quick to prepare and have low fat, saturated fat, and carbohydrate content.
     */

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

    /**
     * Checks whether a given [meal] is quick to prepare (≤ 15 minutes) and has nutrition information.
     */
    private fun isQuickAndHasNutrition(meal: Meal): Boolean {
        return meal.preparationTime != null &&
                meal.preparationTime <= 15 &&
                meal.nutrition != null
    }

    /**
     *
    Calculates a simple health score by summing the total fat, saturated fat, and carbohydrates in the given [meal].
     */
    private fun healthScore(meal: Meal): Double {
        val nutrition = meal.nutrition!!
        return (nutrition.totalFat ?: 0.0) +
                (nutrition.saturatedFat ?: 0.0) +
                (nutrition.carbohydrates ?: 0.0)
    }
}