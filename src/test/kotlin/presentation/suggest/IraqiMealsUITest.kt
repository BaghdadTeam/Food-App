package presentation.suggest

import helpers.suggest.IraqiMealsIdentifierTestData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.example.logic.usecase.suggest.IraqiMealsIdentifierUseCase
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.presentation.suggest.IraqiMealsUI
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IraqiMealsUITest {

    private lateinit var viewer: Viewer
    private lateinit var useCase: IraqiMealsIdentifierUseCase
    private lateinit var ui: IraqiMealsUI

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = IraqiMealsUI(useCase, viewer)
    }

    @Test
    fun `should return iraqi meals and print the name of the meals with it's id`() {
        // Given
        val iraqiMeals = IraqiMealsIdentifierTestData.allIraqiMeals()
        every { useCase.execute() } returns iraqiMeals

        // When
        ui.execute()

        // Then
        verify(exactly = 1) { viewer.log(String.format("%-10s    |  %-20s", "ID", "Name")) }

        iraqiMeals.forEach { meal ->
            verify {
                viewer.log(
                    String.format(
                        "%-10s    |  %-20s",
                        meal.id, meal.name,
                    )
                )
            }
        }
    }

    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        every { useCase.execute() } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is no meals in database.") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        every { useCase.execute() } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is no Iraqi meals found") }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        every { useCase.execute() } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Something wrong happened while retrieving the data.") }
    }
}