package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(): List<Pair<Int, Meal>> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter(::isSeaFoodMeal)
            .sortedByDescending { it.nutrition!!.protein }
            .mapIndexed { index, meal ->
                Pair(index + 1, meal)
            }
            .toList().takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("There is no Sea Food Meals")
    }

    private fun isSeaFoodMeal(meal: Meal): Boolean {
        return meal.tags != null &&
                meal.tags.any { it.contains("seafood", ignoreCase = true) } &&
                meal.nutrition != null &&
                meal.nutrition.protein != null
    }
}