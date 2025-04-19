package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class GuessMealGameUseCase(
    private val mealsProvider: MealsProvider
) {
    fun guessMealPreparationTime(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMeals("No meals found")

        return mealsProvider.getMeals()
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: throw NoElementMatch("No meals found in the database")
    }
}