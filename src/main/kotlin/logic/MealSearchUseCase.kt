package logic.MealSearchUseCase;

import kmpSearch
import model.Meal
import org.example.data.MealsProvider;

class SearchMealUseCase (private val mealsProvider:MealsProvider) {
    private val allMeals = mealsProvider.meals

    fun search(userInput: String): List<Meal> {
        val pattern = userInput.lowercase()
        return allMeals.filter { findMealUsingKMP(it.name!!.lowercase(), pattern) }

    }

    private fun findMealUsingKMP(mealName: String, pattern: String): Boolean {
        if (pattern.isEmpty()) {
            return false
        }

        if (mealName.isEmpty()) {
            return false
        }
        return kmpSearch(mealName, pattern)

    }
}



