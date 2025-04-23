package org.example.presentation

import logic.usecase.GuessMealGameUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GuessGameUI(
    private val useCase: GuessMealGameUseCase,
    private val inputProvider: () -> String = { readlnOrNull() ?: "" },
    private val outputProvider: (String) -> Unit = { println(it) }
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val result = useCase.startGame(inputProvider)
            outputProvider(result.message)
        } catch (e: EmptyMealsException) {
            outputProvider("There are no meals in the database.")
        } catch (e: NoMealFoundException) {
            outputProvider(
                """There are no meals to be used in this game.
                |Please try again later.
            """.trimMargin()
            )
        } catch (e: Exception) {
            outputProvider("Something went wrong while getting the data.")
        }
    }

    companion object {
        const val FEATURE_ID = 5
        const val FEATURE_NAME = "Guess Game"
    }
}