package org.example.logic

import model.Meal
import org.example.data.MealsProvider
import kotlin.collections.filter
import kotlin.collections.flatMap

/**The game selects a meal with ingredients
 * and asks the user to guess one correct ingredient from three choices.
 **/

class IngredientGameUseCase(
    mealsProvider: MealsProvider
) {

    private val allMeals: List<Meal> = mealsProvider.meals
        .filter { !it.ingredients.isNullOrEmpty() && it.name != null && it.id != null }

    private val usedMeals = mutableSetOf<Int>()
    private var score = 0
    private var correctAnswers = 0
    private val points = 1_000

    /**
     * The player is shown a meal name and 3 ingredient choices only 1 correct.
     * The game ends after 15 correct guesses or the first wrong one.
     */
    fun playGame() {
        println("Welcome to the Ingredient Game!")

        while (correctAnswers < 15) {
            val meal = getNextMeal() ?: break
            val correctIngredient = meal.ingredients!!.random()

            val wrongOptions = getWrongIngredients(correctIngredient)
            val options = (listOf(correctIngredient) + wrongOptions).shuffled()

            println("\nWhat is one ingredient in: ${meal.name}?")
            options.forEachIndexed { index, ingredient ->
                println("${index + 1}. $ingredient")
            }

            print("Your choice (1-3): ")
            val choice = readLine()?.toIntOrNull()

            if (choice == null || choice !in 1..3) {
                println("Invalid input. Game over.")
                break
            }

            val selected = options[choice - 1]
            if (selected == correctIngredient) {
                println("Correct! Your score increased by $points ")
                score += points
                correctAnswers++
            } else {
                println("Wrong! The correct ingredient was: $correctIngredient")
                break
            }
        }

        println("\n Game Over! Final Score: $score points ")
    }

    /**
     * Gets the next unused meal that has ingredients.
     */
    private fun getNextMeal(): Meal? {
        return allMeals.firstOrNull { it.id !in usedMeals }?.also {
            usedMeals.add(it.id!!)
        }
    }

    /**
     * Collects two wrong ingredient options from the whole dataset.
     *
     * @param excludeIngredient The correct ingredient to avoid including in wrong options.
     */
    private fun getWrongIngredients(excludeIngredient: String): List<String> {
        return allMeals
            .flatMap { it.ingredients.orEmpty() }
            .filter { !it.equals(excludeIngredient, ignoreCase = true) }
            .distinct()
            .shuffled()
            .take(2)
    }
}

