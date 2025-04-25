package org.example.logic.usecase.game

import logic.MealsProvider
import model.Meal
import org.example.model.GuessFeedback
import org.example.utils.EmptyMealsException

class GuessMealGameUseCase(
    mealsProvider: MealsProvider
) {
    companion object {
        private const val ATTEMPTS = 3
    }

    private val allMeals: List<Meal> = mealsProvider.getMeals()
        .filter { it.name != null && it.preparationTime != null }
        .ifEmpty { throw EmptyMealsException("No meals found") }

    private val meal: Meal = allMeals.random()
    private val correctTime: Int? = meal.preparationTime
    private var attemptsLeft: Int = ATTEMPTS
    private var guessedCorrectly: Boolean = false

    fun getMealName(): String = meal.name!!

    fun getAttemptsLeft(): Int = attemptsLeft

    fun execute(guess: Int?): GuessFeedback {
        if (guess == null) {
            return GuessFeedback.Invalid
        }
        if (guess == correctTime) {
            guessedCorrectly = true
            return GuessFeedback.Correct
        }
        // wrong guess
        attemptsLeft--
        return if (guess < correctTime!!) GuessFeedback.TooLow else GuessFeedback.TooHigh
    }

    fun isGameOver(): Boolean = guessedCorrectly || attemptsLeft <= 0

    fun getCorrectTime(): Int = correctTime!!
}
