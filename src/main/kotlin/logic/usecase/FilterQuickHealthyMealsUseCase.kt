package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {

    fun execute(count: Int): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")

        return mealsProvider.getMeals()
            .filter(::isQuickAndHasNutrition)
            .sortedBy(::healthScore)
            .take(count)
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("There is no more healthy meals")
    }

    private fun isQuickAndHasNutrition(meal: Meal): Boolean {
        return meal.preparationTime <= MAX_PREPARATION_TIME && meal.nutrition != null
    }

    private fun healthScore(meal: Meal): Double {
        val nutrition = meal.nutrition!!
        return (nutrition.totalFat ?: 0.0) +
                (nutrition.saturatedFat ?: 0.0) +
                (nutrition.carbohydrates ?: 0.0)
    }

    companion object {
        const val MAX_PREPARATION_TIME = 15

    }
}
