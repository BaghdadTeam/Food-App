package org.example.presentation.game

import org.example.logic.usecase.game.IngredientGameUseCase
import org.example.model.IngredientQuestion
import org.example.presentation.Feature
import org.example.presentation.Reader
import org.example.presentation.Viewer

class IngredientGameUI(
    private val useCase: IngredientGameUseCase,
    private val viewer: Viewer,
    private val reader: Reader
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            while (!useCase.isGameOver()) {
                val options = useCase.getOptions() ?: break
                viewer.log("\nQuestion ${useCase.getQuestionNumber()} / 15")

                viewer.log("What is one ingredient in: ${useCase.getMealName(options)}?")
                showOptions(options)

                viewer.log("Your choice (1-3): ")
                val choice = reader.readInput()?.toIntOrNull()

                val isCorrect = useCase.execute(options, choice)

                if (isCorrect) {
                    viewer.log("\nCorrect! Your score increased by ${useCase.getPoints()}")
                } else {
                    viewer.log("\nWrong! The correct ingredient was: ${options.correctIngredient}")
                    break
                }
            }

            viewer.log("Game Over! Final Score: ${useCase.getScore()} points")
        } catch (e: NoSuchElementException) {

                viewer.log(
                    """There are no meals for a large group at the moment.
                      |Please try again later.
                    """.trimMargin()
                )

        } catch (e: Exception) {
            viewer.log("Something went wrong while starting the game. Please try again later.")
        }
    }

    private fun showOptions(options: IngredientQuestion) {
        options.options.forEachIndexed { index, ingredient ->
            viewer.log("${index + 1}. $ingredient")
        }
    }

    companion object {
        const val FEATURE_ID = 11
        const val FEATURE_NAME = "Ingredient Game"
    }
}
