package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException

class PotatoLovingMealsUseCase(
    private val mealsProvider: MealsProvider,
) {


    fun execute(): List<Meal> {
        val allMeals = mealsProvider.getMeals()
            .takeIf { it.isNotEmpty() }
            ?: throw EmptyMealsException("There are no meals in the dataset.")

        return allMeals.filter { meal ->
            meal.ingredients?.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true)
            } == true
        }.takeIf { it.isNotEmpty() } ?: throw EmptyMealsException("There is no meal containing potato.")
    }

}
