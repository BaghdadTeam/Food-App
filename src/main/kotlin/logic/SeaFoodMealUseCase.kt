package org.example.logic


import model.Meal
import org.example.data.MealsProvider


class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {
    fun rankSeafoodMeals(): List<Pair<Int, Meal>> {
        val meals = mealsProvider.meals
        return meals.filter { meal ->
            meal.tags!!.contains("seafood")
        }.sortedByDescending { it.nutrition!!.protein }.mapIndexed { index, meal ->
            Pair(index + 1, meal)
        }
    }
}