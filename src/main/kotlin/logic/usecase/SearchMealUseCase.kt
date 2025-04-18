package logic.usecase

import TextSearchUtil
import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealName
import org.example.utils.NoElementMatch
import org.example.utils.EmptyMeals

class SearchMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(name: String): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMeals("No meals found")
        if (name.isEmpty()) throw EmptyMealName("Name should not be empty")
        val meals: List<Meal> = mealsProvider.getMeals().filter {
            findMeal(it.name!!.lowercase(), name.lowercase())
        }
        if (meals.isEmpty()) throw NoElementMatch("No meals found matching '$name'.")
        return meals
    }

}


private fun findMeal(
    mealName: String,
    pattern: String
): Boolean =
    TextSearchUtil.kmpSearch(mealName, pattern)
