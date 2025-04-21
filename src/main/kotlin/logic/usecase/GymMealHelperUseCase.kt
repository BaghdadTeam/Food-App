package logic.usecase

import logic.MealsProvider
import model.Meal

class GymMealHelperUseCase(private val mealProvider: MealsProvider) {

    fun getGymMealsSuggestion(
        targetCalories: Int = 0,
        targetProtein: Double = 0.0,

        ): List<Meal> {
        return mealProvider.getMeals()
            .filter { isSuitableGymMeal(it, targetCalories, targetProtein) }
    }

    private fun isSuitableGymMeal(
        meal: Meal,
        targetCalories: Int,
        targetProtein: Double
    ): Boolean {
        val nutrition = meal.nutrition ?: return false
        val calories = nutrition.calories ?: return false
        val protein = nutrition.protein ?: return false

        return calories in (targetCalories - RANGE_CALORIES).toFloat()..(targetCalories + RANGE_CALORIES).toFloat() &&
                protein in (targetProtein - RANGE_PROTEIN)..(targetProtein + RANGE_PROTEIN)
    }

    companion object {
        const val RANGE_CALORIES: Int = 50
        const val RANGE_PROTEIN = 5
    }
}
