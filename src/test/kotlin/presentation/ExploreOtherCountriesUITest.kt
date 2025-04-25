package presentation

import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.ExploreOtherCountriesFoodCultureUseCase
import org.example.presentation.ExploreOtherCountriesUI
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExploreOtherCountriesUITest{

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var useCase: ExploreOtherCountriesFoodCultureUseCase
    private lateinit var ui: ExploreOtherCountriesUI

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        reader = mockk()
        useCase = mockk()
        ui = ExploreOtherCountriesUI(useCase, viewer, reader)
    }

    @Test
    fun `should show meals when it exist and print it's names`() {
        // Given
        val countryName = "iraq"
        val meals = listOf(
            createMealHelper(name = "iraq salad dish", description = " dish for large group"),
            createMealHelper(name = "Iraq dish", tags = listOf(" dish for for large group")),
        )
        every { reader.readInput() } returns countryName
        every { useCase.execute(countryName) } returns meals

        // When
        ui.execute()

        // Then
        verify { viewer.log("Enter country name to discover it's culture ") }
        verify { viewer.log(
            "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }")}
    }

    @Test
    fun `should show meals when countryName found in meal name or description or tags and print it's names`() {
        // Given
        val countryName = "Italian"
        val meals = listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for families"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for small group"),
            createMealHelper(name = "Italian dish", tags = listOf("indian dish for for one person")),
        )
        every { reader.readInput() } returns countryName
        every { useCase.execute(countryName) } returns meals

        // When
        ui.execute()

        // Then
        verify { viewer.log("Enter country name to discover it's culture ") }
        verify { viewer.log(
            "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }")}
    }

    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        every { reader.readInput() } returns ""
        every { useCase.execute("") } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Enter country name to discover it's culture ") }
        verify { viewer.log("No meals in the database.") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        val countryName = "italy"
        every { reader.readInput() } returns countryName
        every { useCase.execute(countryName) } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Enter country name to discover it's culture ") }
        verify { viewer.log("No meals found matching '$countryName'.") }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        every { reader.readInput() } returns "iraq"
        every { useCase.execute("iraq") } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Enter country name to discover it's culture ") }
        verify { viewer.log("There is something happened when retrieving data") }
    }


}