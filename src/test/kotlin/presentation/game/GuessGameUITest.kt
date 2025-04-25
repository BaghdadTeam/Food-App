package presentation.game

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.example.logic.usecase.game.GuessMealGameUseCase
import org.example.model.GuessFeedback
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.presentation.game.GuessGameUI
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GuessGameUITest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var useCase: GuessMealGameUseCase
    private lateinit var ui: GuessGameUI

    @BeforeEach
    fun setup() {

        viewer = mockk(relaxed = true)
        reader = mockk()
        useCase = mockk(relaxed = true)
        ui = GuessGameUI(useCase, viewer, reader)
    }

    @Test
    fun `successful guess on first try`() {
        // Given
        every { reader.readInput() } returns "5"

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log(match { it.contains("Guess the preparation time for") })
            viewer.log(match { it.contains("Correct!") })
        }
    }

    @Test
    fun `out of attempts and the input is Too low`() {
        // Given
        every { reader.readInput() } returnsMany listOf("1", "1", "1")
        every { useCase.getMealName() } returns "Test Meal"
        every { useCase.getAttemptsLeft() } returnsMany listOf(3, 2, 1)

        every { useCase.execute(any()) } returnsMany listOf(
            GuessFeedback.TooLow,
            GuessFeedback.TooLow,
            GuessFeedback.TooLow
        )

        every { useCase.isGameOver() } returnsMany listOf(false, false, false, true)
        every { useCase.getCorrectTime() } returns 5

        // When
        ui.execute()

        // Then
        verify {
            viewer.log("Out of attempts! The correct preparation time was 5 minutes.")
        }
    }

    @Test
    fun `out of attempts and the input is too high`() {
        // Given
        every { reader.readInput() } returns "10"
        every { useCase.getMealName() } returns "Test Meal"
        every { useCase.getAttemptsLeft() } returns 1
        every { useCase.execute(10) } returns GuessFeedback.TooHigh
        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getCorrectTime() } returns 5

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("Guess the preparation time for “Test Meal” (attempts left: 1):")
            viewer.log("Too high!")
            viewer.log("Out of attempts! The correct preparation time was 5 minutes.")
        }
    }

    @Test
    fun `invalid input shows appropriate message`() {
        // Given
        every { reader.readInput() } returns "abc"
        every { useCase.getMealName() } returns "Test Meal"
        every { useCase.getAttemptsLeft() } returns 1
        every { useCase.execute(null) } returns GuessFeedback.Invalid
        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getCorrectTime() } returns 7

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("Guess the preparation time for “Test Meal” (attempts left: 1):")
            viewer.log("Please enter a valid number.")
            viewer.log("Out of attempts! The correct preparation time was 7 minutes.")
        }
    }

    @Test
    fun `user used null as an input shows appropriate message`() {
        // Given
        every { reader.readInput() } returns "abc"
        every { useCase.getMealName() } returns "Test Meal"
        every { useCase.getAttemptsLeft() } returns 1
        every { useCase.execute(null) } returns GuessFeedback.Invalid
        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getCorrectTime() } returns 7

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("Guess the preparation time for “Test Meal” (attempts left: 1):")
            viewer.log("Please enter a valid number.")
            viewer.log("Out of attempts! The correct preparation time was 7 minutes.")
        }
    }

    @Test
    fun `EmptyMealsException exception is caught`() {
        // Given
        every { reader.readInput() } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log(match { it.contains("There are no meals in the database.") }) }
    }

    @Test
    fun `null input from reader results in invalid guess message`() {
        // Given
        every { reader.readInput() } returns null
        every { useCase.getMealName() } returns "Test Meal"
        every { useCase.getAttemptsLeft() } returns 1
        every { useCase.execute(null) } returns GuessFeedback.Invalid
        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getCorrectTime() } returns 10

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("Guess the preparation time for “Test Meal” (attempts left: 1):")
            viewer.log("Please enter a valid number.")
            viewer.log("Out of attempts! The correct preparation time was 10 minutes.")
        }
    }


    @Test
    fun `NoMealFoundException exception is caught`() {
        // Given
        every { reader.readInput() } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log(match { it.contains("No meal could be selected for the game.") }) }
    }

    @Test
    fun `generic exception is caught`() {
        // Given
        every { reader.readInput() } throws RuntimeException("fail")

        // When
        ui.execute()

        // Then
        verify { viewer.log(match { it.contains("Something went wrong") }) }
    }
}