package org.example.presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GuessMealGameUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Test

class GuessGameUITest {

    private val useCase = mockk<GuessMealGameUseCase>()
    private val inputProvider: () -> String = { "45" }
    private val outputProvider = mockk<(String) -> Unit>(relaxed = true)

    @Test
    fun `should call outputProvider with correct message on successful execution`() {
        // given
        val ui = GuessGameUI(useCase, inputProvider, outputProvider)
        every { useCase.startGame(  inputProvider)
        } returns GuessMealGameUseCase.GameResult(true, "Correct!")

        // when
        ui.execute()

        // then
        verify { outputProvider("Correct!") }
    }

    @Test
    fun `should handle EmptyMealsException and call outputProvider with appropriate message`() {
        // given
        val ui = GuessGameUI(useCase, inputProvider, outputProvider)
        every { useCase.startGame(  inputProvider)
        } throws EmptyMealsException("No meals found")

        // when
        ui.execute()

        // then
        verify { outputProvider("There are no meals in the database.") }
    }

    @Test
    fun `should handle NoMealFoundException and call outputProvider with appropriate message`() {
        // given
        val ui = GuessGameUI(useCase, inputProvider, outputProvider)
        every { useCase.startGame(  inputProvider)
        } throws NoMealFoundException("No meals found in this game.")

        // when
        ui.execute()

        // then
        verify { outputProvider("""There are no meals to be used in this game.
            |Please try again later.""".trimMargin()) }
    }

    @Test
    fun `should handle general Exception and call outputProvider with appropriate message`() {
        // given
        val ui = GuessGameUI(useCase, inputProvider, outputProvider)
        every { useCase.startGame(  inputProvider)
        } throws Exception("Something went wrong")

        // when
        ui.execute()

        // then
        verify { outputProvider("Something went wrong while getting the data.") }
    }
}