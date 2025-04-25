package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException

class ExploreOtherCountriesFoodCultureUseCase(private val mealsProvider: MealsProvider) {


    fun execute(countryName: String): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")

        val allMeals = mealsProvider.getMeals()
            .filter { grtMealsContainInput(it, countryName) }
            .shuffled()
            .takeIf { it.isNotEmpty() }

        if (allMeals.isNullOrEmpty())  throw NoSuchElementException("No Such element for this country")

        return getNumbersOfMeals(allMeals)

    }

    private fun grtMealsContainInput(meal: Meal, input: String): Boolean {
        return (meal.name?.contains(input, ignoreCase = true) == true
                || meal.tags?.any { it.contains(input, ignoreCase = true) } == true
                || meal.description?.contains(input, ignoreCase = true) == true)
    }

    private fun getNumbersOfMeals(meals: List<Meal>?): List<Meal> {
        if (meals.isNullOrEmpty()) return emptyList()
        return if (meals.size < 20) meals
        else meals.take(20)
    }

}