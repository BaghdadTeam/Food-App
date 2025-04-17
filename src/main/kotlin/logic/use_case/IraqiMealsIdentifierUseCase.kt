package logic.use_case

import model.Meal
import org.example.data.MealsProvider

/**
 * Use case for identifying Iraqi meals from a list of meals.
 *
 * @property mealsProvider Provides the list of meals to filter.
 */
class IraqiMealsIdentifierUseCase(
    private val mealsProvider: MealsProvider
) {

    /**
     * Retrieves a list of meals that are identified as Iraqi.
     *
     * @return A list of meals that are tagged or described as Iraqi.
     */
    fun getIraqiMeals(): List<Meal> {
        return mealsProvider.meals.filter(::isIraqiMeal)
    }

    /**
     * Determines if a given meal is Iraqi based on its tags or description.
     *
     * @param meal The meal to evaluate.
     * @return `true` if the meal is Iraqi, `false` otherwise.
     */
    private fun isIraqiMeal(meal: Meal): Boolean {
        val iraqiTag = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true
        val iraqDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return iraqiTag || iraqDesc
    }
}