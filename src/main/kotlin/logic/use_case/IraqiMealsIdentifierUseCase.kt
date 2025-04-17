package logic.use_case

import model.Meal
import org.example.logic.MealsProvider

class IraqiMealsIdentifierUseCase(
    private val mealsProvider: MealsProvider
) {

    fun getIraqiMeals(): List<Meal> {

        return mealsProvider.getMeals()
            .filter(::isIraqiMeal)
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("There is no Iraqi Meals")
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
        val iraqiTag = meal.tags?.any { it.contains("iraqi", ignoreCase = true) } == true
        val iraqDesc = meal.description?.contains("iraq", ignoreCase = true) == true
        return iraqiTag || iraqDesc
    }
}