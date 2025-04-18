package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.model.IngredientQuestion

class IngredientGameUseCase(
    mealsProvider: MealsProvider
) {
    private val allMeals: List<Meal> = mealsProvider.getMeals()
        .filter { !it.ingredients.isNullOrEmpty() && it.name != null && it.id != null }

    private val usedMeals = mutableSetOf<Int>()
    private var score = 0
    private var correctAnswers = 0
    private val points = 1_000
    private val maxQuestions = 15

    fun execute() {
        while (isGameOver()) {

            val options = getOptions() ?: break
            println("\nWhat is one ingredient in: ${options.mealName}?")
            options.options.forEachIndexed { index, ingredient ->
                println("${index + 1}. $ingredient")
            }

            print("Your choice (1-3): ")
            val choice = readLine()?.toIntOrNull()

            if (isNotValidChoice(choice)) {
                println("Invalid input. Game over.")
                break
            }

            if (correctAnswer(options, choice)) {
                println("Correct! Your score increased by $points ")
            } else {
                println("Wrong! The correct ingredient was: ${options.correctIngredient}")
                break
            }
        }

        println("\n Game Over! Final Score: $score points ")
    }

    private fun correctAnswer(
        options: IngredientQuestion,
        choice: Int?
    ): Boolean {
        val selected = options.options[choice?.minus(1) ?: return false]
        if (selected == options.correctIngredient) {
            score += points
            correctAnswers++
            return true
        }
        return false
    }

    private fun isNotValidChoice(choice: Int?): Boolean {
        return choice == null || choice !in 1..3
    }

    private fun getOptions(): IngredientQuestion? {
        val meal = getNextMeal() ?: return null
        val correctIngredient = meal.ingredients!!.random()
        val wrongOptions = getWrongIngredients(correctIngredient)
        val options = (listOf(correctIngredient) + wrongOptions).shuffled()

        return IngredientQuestion(meal.name.toString(), correctIngredient, options)
    }

    private fun isGameOver(): Boolean = correctAnswers < maxQuestions

    private fun getNextMeal(): Meal? {
        return allMeals.firstOrNull { it.id !in usedMeals }?.also {
            usedMeals.add(it.id!!)
        }
    }

    private fun getWrongIngredients(excludeIngredient: String): List<String> {
        return allMeals
            .flatMap { it.ingredients.orEmpty() }
            .filter { !it.equals(excludeIngredient, ignoreCase = true) }
            .distinct()
            .shuffled()
            .take(2)
    }
}