package logic.MealSearchUseCase;

import kmpSearch
import model.Meal
import org.example.data.MealsProvider;

class SearchMealUseCase (private val mealsProvider:MealsProvider) {

    fun search(userInput: String): List<Meal> {
        val pattern = userInput.lowercase()
        return mealsProvider.meals.filter { findMealUsingKMP(it.name!!.lowercase(), pattern) }

    }

    private fun findMealUsingKMP(mealName: String, pattern: String): Boolean {
        return when {
            pattern.isEmpty() || mealName.isEmpty() -> false
            
            else -> kmpSearch(mealName, pattern)
        }
    }
}



