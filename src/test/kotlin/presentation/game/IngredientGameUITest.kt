package presentation.game

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.usecase.game.IngredientGameUseCase
import org.example.model.IngredientQuestion
import org.example.presentation.game.IngredientGameUI
import org.example.presentation.Reader
import org.example.presentation.Viewer
import kotlin.test.BeforeTest
import kotlin.test.Test

class IngredientGameUITest {

    private lateinit var useCase: IngredientGameUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var ingredientGameUI: IngredientGameUI

    private val question = IngredientQuestion(
        mealName = "Pizza",
        correctIngredient = "Cheese",
        options = listOf("Cheese", "Tomato", "Basil")
    )

    @BeforeTest
    fun setUp() {
        useCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        reader = mockk()

        ingredientGameUI = IngredientGameUI(useCase, viewer, reader)
    }

    @Test
    fun `should show question including the correct option and end on wrong answer`() {


        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getOptions() } returns question
        every { useCase.getMealName(question) } returns "Pizza"
        every { reader.readInput() } returns "2" // wrong answer
        every { useCase.execute(question, 2) } returns false
        every { useCase.getScore() } returns 0

        ingredientGameUI.execute()

        verify { viewer.log(match { it.contains("Question") }) }
        verify { viewer.log("What is one ingredient in: Pizza?") }
        verify { viewer.log("1. Cheese") }
        verify { viewer.log("Your choice (1-3): ") }
        verify { viewer.log("Game Over! Final Score: 0 points") }
    }

    @Test
    fun `should continue playing when answer is correct`() {

        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getOptions() } returns question
        every { useCase.getMealName(question) } returns "Pizza"
        every { reader.readInput() } returns "1" // correct answer
        every { useCase.execute(question, 1) } returns true
        every { useCase.getPoints() } returns 1000
        every { useCase.getScore() } returns 1000

        ingredientGameUI.execute()

        verify { viewer.log("\nCorrect! Your score increased by 1000") }
        verify { viewer.log("Game Over! Final Score: 1000 points") }
    }

    @Test
    fun `should handle NoSuchElementException and print error message`() {
        every { useCase.isGameOver() } throws NoSuchElementException()

        ingredientGameUI.execute()

        verify {
            viewer.log(
                match {
                    it.contains("There are no meals for a large group at the moment")
                }
            )
        }
    }


    @Test
    fun `should handle unexpected exceptions gracefully`() {
        every { useCase.isGameOver() } throws RuntimeException("Unexpected error")

        ingredientGameUI.execute()

        verify { viewer.log("Something went wrong while starting the game. Please try again later.") }
    }

    @Test
    fun `should exit when user input null value`() {
        every { useCase.isGameOver() } returns false
        every { useCase.getOptions() } returns null
        every { useCase.getScore() } returns 2000

        ingredientGameUI.execute()

        verify { viewer.log("Game Over! Final Score: 2000 points") }
    }

    @Test
    fun `should exit when entered null choice`() {
        every { useCase.isGameOver() } returnsMany listOf(false, true)
        every { useCase.getOptions() } returns question
        every { reader.readInput() } returns null
        every { useCase.getScore() } returns 0

        ingredientGameUI.execute()

        verify { viewer.log("\nWrong! The correct ingredient was: Cheese") }
        verify { viewer.log("Game Over! Final Score: 0 points") }
    }

}
