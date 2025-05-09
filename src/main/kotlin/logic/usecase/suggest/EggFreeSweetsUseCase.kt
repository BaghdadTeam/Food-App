package org.example.logic.usecase.suggest

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class EggFreeSweetsUseCase(
    private val mealsProvider: MealsProvider
) {

    private val seenMeals = mutableSetOf<String>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter(::isEggFreeSweet)
            .takeIf { it.isNotEmpty() }
            ?.random()
            .also { seenMeals.add(it?.id.toString()) }
            ?: throw NoMealFoundException("There is no more egg free sweets")
    }

    private fun isEggFreeSweet(meal: Meal): Boolean {
        if (meal.tags == null) return false

        val sweetMeal = meal.tags.any { it.contains("sweet", ignoreCase = true) }
        if (!sweetMeal) return false

        if (meal.ingredients == null) return false
        val containsEgg = meal.ingredients.any { it.contains("egg", ignoreCase = true) }
        if (containsEgg) return false

        val seen = seenMeals.contains(meal.id.toString())
        return !seen
    }
}