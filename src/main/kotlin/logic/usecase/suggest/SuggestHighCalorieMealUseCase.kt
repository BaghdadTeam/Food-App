package org.example.logic.usecase.suggest

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<String>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter(::isHighCalorieMeal)
            .takeIf { it.isNotEmpty() }
            ?.random()
            .also { suggestedMeals.add(it?.id.toString()) }
            ?: throw NoMealFoundException("There is no High Calories meals")
    }


    private fun isHighCalorieMeal(meal: Meal):Boolean{
        if (meal.nutrition == null) return false
        return meal.nutrition.calories!! > 700 && !suggestedMeals.contains(meal.id.toString())

    }


}