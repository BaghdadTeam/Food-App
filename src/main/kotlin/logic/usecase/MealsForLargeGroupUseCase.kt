package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class MealsForLargeGroupUseCase(private val mealsProvider: MealsProvider) {

    private val countryName = "Italy"
    private val query = "Large group"
    fun execute(): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter { grtMealsContainInput(it, countryName) && grtMealsContainInput(it, query) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("There is no item found")
    }

    private fun grtMealsContainInput(meal: Meal, input: String): Boolean {
        return (meal.name?.contains(input, ignoreCase = true) == true
                || meal.tags?.any { it.contains(input, ignoreCase = true) } == true
                || meal.description?.contains(input, ignoreCase = true) == true)
    }
}