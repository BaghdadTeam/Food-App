package logic.usecase

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
        val inTags = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true
        if (inTags) return true
        val inDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return inDesc
    }
}