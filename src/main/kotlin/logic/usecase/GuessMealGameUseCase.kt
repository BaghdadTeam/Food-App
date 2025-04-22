package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GuessMealGameUseCase(
    private val mealsProvider: MealsProvider
) {
    fun guessMealPreparationTime(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")

        return mealsProvider.getMeals()
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: throw NoMealFoundException("No meals found in the database")
    }
}