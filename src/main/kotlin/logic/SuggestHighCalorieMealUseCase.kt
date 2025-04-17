package org.example.logic

import model.Meal

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<Meal>()

    fun suggestMeal(): Meal {

        return mealsProvider.getMeals()
            .filter(::isHighCalorieMeal)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { suggestedMeals.add(it) }
            ?: throw NoSuchElementException("There is no High Calories meals")
    }

    fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition?.calories!! > 700 && !suggestedMeals.contains(meal)
}