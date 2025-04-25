package org.example.presentation.game

import org.example.logic.usecase.game.GuessMealGameUseCase
import org.example.model.GuessFeedback
import org.example.presentation.Feature
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GuessGameUI(
    private val useCase: GuessMealGameUseCase,
    private val viewer: Viewer,
    private val reader: Reader
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            // game loop
            while (!useCase.isGameOver()) {
                viewer.log(
                    "Guess the preparation time for “${useCase.getMealName()}” " +
                            "(attempts left: ${useCase.getAttemptsLeft()}):"
                )
                val input = reader.readInput()
                val guess = input?.toIntOrNull()

                when (useCase.execute(guess)) {
                    GuessFeedback.Correct -> {
                        viewer.log("Correct! It takes ${useCase.getCorrectTime()} minutes.")
                        return
                    }
                    GuessFeedback.TooLow   -> viewer.log("Too low!")
                    GuessFeedback.TooHigh  -> viewer.log("Too high!")
                    GuessFeedback.Invalid  -> viewer.log("Please enter a valid number.")
                }
            }

            // ran out of attempts
            viewer.log(
                "Out of attempts! The correct preparation time was " +
                        "${useCase.getCorrectTime()} minutes."
            )

        } catch (e: EmptyMealsException) {
            viewer.log("There are no meals in the database.")
        } catch (e: NoMealFoundException) {
            viewer.log("No meal could be selected for the game.")
        } catch (e: Exception) {
            viewer.log("Something went wrong while starting the game.")
        }
    }

    private companion object {
        const val FEATURE_ID   = 5
        const val FEATURE_NAME = "Guess Game"
    }
}