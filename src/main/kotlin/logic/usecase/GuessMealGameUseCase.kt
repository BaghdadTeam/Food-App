package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.logic.MockInputProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GuessMealGameUseCase(
    private val mealsProvider: MealsProvider,
    private val inputProvider: MockInputProvider
) {
    companion object {
        var attempts = 3
    }

    fun startGame(inputProvider: () -> String): GameResult {
        val meal = guessMealPreparationTime()

        for (attempt in 1..attempts) {
            println("Guess the preparation time for ${meal.name}:")
            val guess = inputProvider().toIntOrNull()

            if (guess != null) {
                when {
                    guess == meal.preparationTime -> {
                        return GameResult(true, "Correct! The preparation time is" +
                                " ${meal.preparationTime} minutes.")
                    }
                    guess < meal.preparationTime -> println("Too low!")
                    else -> println("Too high!")
                }
                attempts--
            } else {
                println("Invalid input. Please enter a valid number.")
            }
        }

        return GameResult(false, "The correct time was" +
                " ${meal.preparationTime} minutes.")
    }

    fun guessMealPreparationTime(): Meal {
        val meals = mealsProvider.getMeals()
        if (meals.isEmpty()) throw EmptyMealsException("No meals found")

        return meals.randomOrNull()
           ?: throw NoMealFoundException("No meals found in the database")

    }

    data class GameResult(val isSuccessful: Boolean, val message: String)
}