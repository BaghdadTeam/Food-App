import logic.MealsProvider
import model.Meal

class GymMealHelperUseCase(private val mealProvider: MealsProvider) {

    fun getGymMealsSuggestion(
        targetCalories: Int = 0,
        targetProtein: Double = 0.0,
        approximateTarget: Int = 50
    ): List<Meal> {
        return mealProvider.getMeals()
            .filter { isSuitableGymMeal(it, targetCalories, targetProtein, approximateTarget) }
    }

    private fun isSuitableGymMeal(
        meal: Meal,
        targetCalories: Int,
        targetProtein: Double,
        range: Int
    ): Boolean {
        val nutrition = meal.nutrition ?: return false
        val calories = nutrition.calories ?: return false
        val protein = nutrition.protein ?: return false

        return calories in (targetCalories - range).toFloat()..(targetCalories + range).toFloat() &&
                protein in (targetProtein - range)..(targetProtein + range)
    }
}
