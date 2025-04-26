package logic.usecase.filter

import logic.MealsProvider
import model.Meal
import model.Nutrition
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GymMealHelperUseCase(
    private val mealProvider: MealsProvider,
) {

    fun getGymMealsSuggestion(
        targetCalories: Double = 0.0,
        targetProtein: Double = 0.0,
    ): List<Meal> {
        val meals = mealProvider.getMeals()
        if (meals.isEmpty()) {
            throw EmptyMealsException("No meals available in the provider")
        }

        return meals.filter { isSuitableGymMeal(it, targetCalories, targetProtein) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("No meals match the given calorie/protein criteria")
    }

    private fun isSuitableGymMeal(
        meal: Meal,
        targetCalories: Double,
        targetProtein: Double
    ): Boolean {
        if (meal.name.isNullOrBlank()) {
            return false
        }

        val nutrition = meal.nutrition ?: return false
        return when {
            targetCalories > 0 && targetProtein > 0 ->
                matchesCalories(nutrition, targetCalories) &&
                        matchesProtein(nutrition, targetProtein)

            targetCalories > 0 -> matchesCalories(nutrition, targetCalories)
            targetProtein > 0 -> matchesProtein(nutrition, targetProtein)
            else -> true
        }
    }

    private fun matchesCalories(nutrition: Nutrition, target: Double): Boolean {
        return nutrition.calories?.let { calories ->
            calories in (target - RANGE_CALORIES)..(target + RANGE_CALORIES)
        } == true
    }

    private fun matchesProtein(nutrition: Nutrition, target: Double): Boolean {
        return nutrition.protein?.let { protein ->
            protein in (target - RANGE_PROTEIN)..(target + RANGE_PROTEIN)
        } == true
    }

    companion object {
        const val RANGE_CALORIES = 50.0
        const val RANGE_PROTEIN = 5.0
    }
}
