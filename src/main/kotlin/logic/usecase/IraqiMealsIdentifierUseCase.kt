package logic.usecase

import model.Meal
import logic.MealsProvider

class IraqiMealsIdentifierUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(): List<Meal> =
        mealsProvider.getMeals()
            .filter(::isIraqiMeal)
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("There is no Iraqi Meals")


    private fun isIraqiMeal(meal: Meal): Boolean {
        val inTags = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true
        if (inTags) return true
        val inDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return inDesc
    }
}