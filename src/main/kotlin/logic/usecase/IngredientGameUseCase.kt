package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.model.IngredientQuestion

class IngredientGameUseCase(mealsProvider: MealsProvider) {
    private val allMeals: List<Meal> = mealsProvider.getMeals()
        .filter { !it.ingredients.isNullOrEmpty() && it.name != null && it.id != null }

    private val usedMeals = mutableSetOf<Int>()
    private var score = 0
    private var correctAnswers = 0
    private val points = 1_000
    private val maxQuestions = 15

    fun execute(options: IngredientQuestion, choice: Int?): Boolean {
        return if (isNotValidChoice(options, choice)) false
        else correctAnswer(options, choice!!)
    }

    fun getScore(): Int = score

    fun getPoints(): Int = points

    fun getQuestionNumber(): Int = correctAnswers + 1

    fun getMealName(options: IngredientQuestion?): String {
        return options?.mealName.toString()
    }

    fun isGameOver(): Boolean = correctAnswers >= maxQuestions

    private fun correctAnswer(
        options: IngredientQuestion,
        choice: Int
    ): Boolean {
        val selected = options.options[choice.minus(1)]
        if (selected == options.correctIngredient) {
            score += points
            correctAnswers++
            return true
        }
        return false
    }

    private fun isNotValidChoice(options: IngredientQuestion, choice: Int?): Boolean {
        return choice == null || choice !in 1..options.options.size
    }

    fun getOptions(): IngredientQuestion? {
        val meal = getNextMeal() ?: return null
        val correctIngredient = meal.ingredients!!.random()
        val wrongOptions = getWrongIngredients(correctIngredient)
        val options = (listOf(correctIngredient) + wrongOptions).shuffled()

        return IngredientQuestion(meal.name.toString(), correctIngredient, options)
    }

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