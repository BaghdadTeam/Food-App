package org.example.presentation

import logic.usecase.IngredientGameUseCase
import org.example.model.IngredientQuestion

class IngredientGameUI(private val useCase: IngredientGameUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            while (!useCase.isGameOver()) {
                val options = useCase.getOptions() ?: break
                println("\nQuestion ${useCase.getQuestionNumber()} / 15")

                println("What is one ingredient in: ${useCase.getMealName(options)}?")
                showOptions(options)

                print("Your choice (1-3): ")
                val choice = readLine()?.toIntOrNull()

                val isCorrect = useCase.execute(options, choice)

                if (isCorrect) {
                    println("\nCorrect! Your score increased by ${useCase.getPoints()}")
                } else {
                    println("\nWrong! The correct ingredient was: ${options.correctIngredient}")
                    break
                }
            }

            println("Game Over! Final Score: ${useCase.getScore()} points")
        } catch (e: NoSuchElementException) {
            println(
                println(
                    """There are no meals for a large group at the moment.
                      |Please try again later.
                    """.trimMargin()
                )
            )
        } catch (e: Exception) {
            println("Something went wrong while starting the game. Please try again later.")
        }
    }

    private fun showOptions(options: IngredientQuestion) {
        options.options.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        }
    }

    companion object {
        const val FEATURE_ID = 11
        const val FEATURE_NAME = "Ingredient Game"
    }
}
