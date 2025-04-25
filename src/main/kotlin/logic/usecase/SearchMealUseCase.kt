package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.logic.SearchAlgorithm
import org.example.utils.*

class SearchMealUseCase(
    private val mealsProvider: MealsProvider,
    private val searchAlgorithm: SearchAlgorithm
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


    private fun findMeal(
        mealName: String,
        pattern: String
    ): Boolean =
        searchAlgorithm.search(mealName, pattern)

}

