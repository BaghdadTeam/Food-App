package logic.use_case

import model.Meal
import logic.MealsProvider

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
        return meal.preparationTime <= MAX_PREPARATION_TIME && meal.nutrition != null
    }

    private fun healthScore(meal: Meal): Double {
        val nutrition = meal.nutrition!!
        return (nutrition.totalFat ?: LEAST_TOTAL_FAT) +
                (nutrition.saturatedFat ?: LEAST_SATURATED_FAT) +
                (nutrition.carbohydrates ?: LEAST_CARBOHYDRATES)
    }

    companion object {
        const val MAX_PREPARATION_TIME = 15
        const val LEAST_SATURATED_FAT  = 0.0
        const val LEAST_CARBOHYDRATES = 0.0
        const val LEAST_TOTAL_FAT= 0.0
        
}}
