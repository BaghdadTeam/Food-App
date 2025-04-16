package org.example.logic


import org.example.data.MealsProvider


class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {
    fun rankSeafoodMeals() {
        val meals=mealsProvider.meals
        meals.filter { meal ->
            meal.tags!!.contains("seafood")
        }.sortedByDescending { it.nutrition!!.protein }.mapIndexed { index, meal ->
            Pair(index + 1, meal)
        }
    }
}