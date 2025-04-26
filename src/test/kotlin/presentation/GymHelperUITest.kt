package org.example.presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.filter.GymMealHelperUseCase
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymHelperUITest {
    private lateinit var useCase: GymMealHelperUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var gymHelperUI: GymHelperUI

    @BeforeEach
    fun setup() {
        useCase = mockk()
        viewer = mockk(relaxed = true)
        reader = mockk()
        gymHelperUI = GymHelperUI(useCase, viewer, reader)
    }

    @Test
    fun `should have correct feature ID and name`() {
        // Given & When & Then
        assertThat(gymHelperUI.id).isEqualTo(9)
        assertThat(gymHelperUI.name).isEqualTo("Gym helper")
    }

    @Test
    fun `should implement Feature interface`() {
        // Given & When & Then
        assertThat(gymHelperUI).isInstanceOf(Feature::class.java)
    }

    @Test
    fun `should prompt for calories and protein inputs`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", "30")
        every { useCase.getGymMealsSuggestion(any(), any()) } returns emptyList()
        // When
        gymHelperUI.execute()
        // Then
        verify {
            viewer.log(" Enter desired calories: ")
            viewer.log(" Enter desired protein : ")
        }
    }

    @Test
    fun `should handle valid numeric inputs`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", "30")
        every { useCase.getGymMealsSuggestion(500.0, 30.0) } returns emptyList()
        // When
        gymHelperUI.execute()
        // Then
        verify { useCase.getGymMealsSuggestion(500.0, 30.0) }
    }

    @Test
    fun `should show error message for invalid calories input`() {
        // Given
        every { reader.readInput() } returns "invalid"
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("Error: Invalid number") }
    }

    @Test
    fun `should show error message for invalid protein input`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", "invalid")
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("Error: Invalid number") }
    }

    @Test
    fun `should handle empty input for calories`() {
        // Given
        every { reader.readInput() } returns null
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("Error: Invalid number") }
    }

    @Test
    fun `should show no meals message when none found`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", "30")
        every { useCase.getGymMealsSuggestion(any(), any()) } returns emptyList()
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("No meals found matching your nutritional goals.") }
    }

    @Test
    fun `should display matching meals using MealTablePrinter when found`() {
        // Given
        val mockMeal = mockk<Meal>()
        every { reader.readInput() } returnsMany listOf("500", "30")
        every { useCase.getGymMealsSuggestion(any(), any()) } returns listOf(mockMeal)
        val mockPrinter = mockk<MealTablePrinter>(relaxed = true)
        // override factory to inject mock
        val uiWithMockPrinter = GymHelperUI(useCase, viewer, reader) { mockPrinter }
        // When
        uiWithMockPrinter.execute()
        // Then
        verify {
            viewer.log("\n Meals matching your gym goals:\n")
            mockPrinter.print(listOf(mockMeal), isGymHelperUI = true)
        }
    }

    @Test
    fun `should handle exceptions from use case gracefully`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", "30")
        every { useCase.getGymMealsSuggestion(any(), any()) } throws Exception("Use case error")
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("Error: Use case error") }
    }

    @Test
    fun `should show error message for empty protein input`() {
        // Given
        every { reader.readInput() } returnsMany listOf("500", null)
        // When
        gymHelperUI.execute()
        // Then
        verify { viewer.log("Error: Invalid number") }
    }
}
