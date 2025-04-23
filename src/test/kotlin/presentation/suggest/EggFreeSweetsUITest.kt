package presentation.suggest

import helpers.suggest.EggFreeSweetsTestData
import io.kotest.matchers.string.match
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.example.logic.usecase.suggest.EggFreeSweetsUseCase
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.presentation.suggest.EggFreeSweetsUI
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class EggFreeSweetsUITest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var useCase: EggFreeSweetsUseCase
    private lateinit var ui: EggFreeSweetsUI

    @BeforeEach
    fun setup() {
        reader = mockk()
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = EggFreeSweetsUI(useCase, viewer, reader)
    }

    @Test
    fun `should show sweet and print details when user says yes`() {
        // Given
        val sweet = EggFreeSweetsTestData.eggFreeCoconutLadoo()
        every { useCase.execute() } returns sweet
        every { reader.readInput() } returns "yes"

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("How about this sweet: ${sweet.name}")
            viewer.log("Do you like it? (yes/no): ")
        }
    }

    @Test
    fun `should keep asking until the answer is yes`() {
        // Given
        val sweet = EggFreeSweetsTestData.eggFreeCoconutLadoo()
        every { useCase.execute() } returns sweet
        every { reader.readInput() } returnsMany listOf("no", "No", "n", "yes")

        // When
        ui.execute()

        // Then
        verify(exactly = 4) { viewer.log("How about this sweet: ${sweet.name}") }
        verify(exactly = 4) { viewer.log(match { it.contains("Do you like it? (yes/no)") }) }
    }

    @ParameterizedTest
    @CsvSource("yes", "ys", "YeS  ", "  Yup", "y ")
    fun `should accept the different styles of yes regardless of extra spaces or case`(input: String) {
        // Given
        val sweet = EggFreeSweetsTestData.eggFreeCoconutLadoo()
        every { useCase.execute() } returns sweet
        every { reader.readInput() } returns input

        // When
        ui.execute()

        // Then
        verify(exactly = 1) { viewer.log("How about this sweet: ${sweet.name}") }
        verify(exactly = 1) { viewer.log(match { it.contains("Do you like it? (yes/no)") }) }
    }

    @Test
    fun `should handle if the input is null`() {
        // Given
        val sweet = EggFreeSweetsTestData.eggFreeCoconutLadoo()
        every { useCase.execute() } returns sweet
        every { reader.readInput() } returnsMany listOf(null, "yes")

        // When
        ui.execute()

        // Then
        verify(exactly = 2) { viewer.log("How about this sweet: ${sweet.name}") }
        verify(exactly = 2) { viewer.log(match { it.contains("Do you like it? (yes/no)") }) }
    }

    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        every { useCase.execute() } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is no meals in database") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        every { useCase.execute() } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is no egg free sweets found") }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        every { useCase.execute() } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Something wrong happened when retrieving the data.") }
    }
}