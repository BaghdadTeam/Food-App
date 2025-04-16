package org.example.logic

import model.Meal
import org.example.data.MealsProvider

class SuggestEasyMealUseCase(private val mealsRepository: MealsProvider) {

    fun getRandomEasyMeals(): List<Meal> {
        return mealsRepository.meals
            .filter(::isEasyMeal)
            .shuffled()
            .take(10)
    }

    private fun isEasyMeal(meal: Meal): Boolean =
        (meal.preparationTime != null && meal.preparationTime <= 30) &&
                (meal.ingredients?.size != null && meal.ingredients.size <= 5) &&
                (meal.steps.size <= 6)

}
