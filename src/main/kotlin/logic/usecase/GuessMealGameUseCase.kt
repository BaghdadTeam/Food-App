package logic.usecase

import logic.MealsProvider
import model.Meal

class GuessMealGameUseCase(
    private val mealsProvider: MealsProvider
) {
    fun guessMealPreparationTime(): Meal {

        return mealsProvider.getMeals()
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: throw NoSuchElementException("No meals found in the database")
    }
}