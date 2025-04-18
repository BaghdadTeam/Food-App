package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class SeaFoodMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(): List<Pair<Int, Meal>> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMeals("No meals found")
        return mealsProvider.getMeals()
            .filter { meal ->
                meal.tags!!.contains("seafood")
            }.sortedByDescending {
                it.nutrition!!.protein
            }.mapIndexed { index, meal ->
                Pair(index + 1, meal)
            }.takeIf { it.isNotEmpty() }
            ?: throw NoElementMatch("There is no Sea Food Meals")
    }
}