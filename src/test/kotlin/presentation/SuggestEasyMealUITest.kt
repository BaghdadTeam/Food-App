package org.example.presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.SuggestEasyMealUseCase
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestEasyMealUITest {
    private lateinit var useCase: SuggestEasyMealUseCase
    private lateinit var viewer: Viewer
    private lateinit var suggestEasyMealUI: SuggestEasyMealUI

    @BeforeEach
    fun setup() {
        useCase = mockk()
        viewer = mockk(relaxed = true)
        suggestEasyMealUI = SuggestEasyMealUI(useCase, viewer)
    }

    @Test
    fun `should have correct feature ID and name`() {
        assertThat(suggestEasyMealUI.id).isEqualTo(4)
        assertThat(suggestEasyMealUI.name).isEqualTo("Suggest easy meal")
    }

    @Test
    fun `should implement Feature interface`() {
        assertThat(suggestEasyMealUI).isInstanceOf(Feature::class.java)
    }

    @Test
    fun `should display no meals message when none available`() {
        // Given
        every { useCase.execute() } returns emptyList()

        // When
        suggestEasyMealUI.execute()

        // Then
        verify { viewer.log("No easy meals available.") }
    }

    @Test
    fun `should display meals using MealTablePrinter when available`() {
        // Given
        val mockMeal = mockk<Meal>()
        every { useCase.execute() } returns listOf(mockMeal)
        val mockPrinter = mockk<MealTablePrinter>(relaxed = true)

        // Inject mock printer (requires modifying SuggestEasyMealUI)
        val uiWithMockPrinter = SuggestEasyMealUI(useCase, viewer) { mockPrinter }

        // When
        uiWithMockPrinter.execute()

        // Then
        verify { mockPrinter.print(listOf(mockMeal), isGymHelperUI = false) }
    }

    @Test
    fun `should handle exceptions from use case gracefully`() {
        // Given
        every { useCase.execute() } throws Exception("Test error")

        // When
        suggestEasyMealUI.execute()

        // Then
        verify { viewer.log("Error: Test error") }
    }
}