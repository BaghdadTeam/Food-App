package logic.usecase

import model.Meal
import logic.MealsProvider

class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(): List<Pair<Int, Meal>> {

        return mealsProvider.getMeals()
            .filter { meal ->
                meal.tags!!.contains("seafood")
            }.sortedByDescending {
                it.nutrition!!.protein
            }.mapIndexed { index, meal ->
                Pair(index + 1, meal)
            }.takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("There is no Sea Food Meals")
    }
}