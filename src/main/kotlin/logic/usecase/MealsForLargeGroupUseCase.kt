package logic.usecase

import logic.MealsProvider
import model.Meal

class MealsForLargeGroupUseCase(private val mealsProvider: MealsProvider) {

    private val countryName = "Italy"
    private val query = "Large group"
    fun execute(): List<Meal> =
        mealsProvider.getMeals()
            .filter {grtMealsContainInput(it, countryName) && grtMealsContainInput(it, query)}

    private fun grtMealsContainInput(meal: Meal, input: String): Boolean {
        return (meal.name?.contains(input, ignoreCase = true) == true
                || meal.tags?.any{it.contains(input, ignoreCase = true)} == true
                || meal.description?.contains(input, ignoreCase = true) == true)
    }

}