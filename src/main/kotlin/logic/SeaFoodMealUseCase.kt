package org.example.logic

import model.Meal
import org.example.data.DefaultMealsProvider

class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun rankSeafoodMeals(): List<Pair<Int, Meal>> {

        return mealsProvider.getMeals()
            .filter { meal ->
                meal.tags!!.contains("seafood")
            }.sortedByDescending {
                it.nutrition!!.protein
            }.mapIndexed { index, meal ->
                Pair(index + 1, meal)
            }
    }
}