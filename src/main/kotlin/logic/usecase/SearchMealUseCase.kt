package logic.usecase

import TextSearchUtil
import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealNameException
import org.example.utils.NoMealFoundException
import org.example.utils.EmptyMealsException

class SearchMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(name: String): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        if (name.isEmpty()) throw EmptyMealNameException("Name should not be empty")
        val meals: List<Meal> = mealsProvider.getMeals().filter {
            findMeal(it.name!!.lowercase(), name.lowercase())
        }
        if (meals.isEmpty()) throw NoMealFoundException("No meals found matching '$name'.")
        return meals
    }

}


private fun findMeal(
    mealName: String,
    pattern: String
): Boolean =
    TextSearchUtil.kmpSearch(mealName, pattern)
