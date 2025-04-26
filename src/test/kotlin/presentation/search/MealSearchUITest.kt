package presentation.search

import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.SearchMealUseCase
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.presentation.search.MealSearchUI
import org.example.utils.EmptyMealNameException
import org.example.utils.EmptyMealsException
import org.example.utils.EmptyPatternException
import org.example.utils.EmptyTextException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MealSearchUITest {
    private lateinit var useCase: SearchMealUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var ui: MealSearchUI

    @BeforeEach
    fun setUp() {
        reader = mockk()
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = MealSearchUI(useCase, viewer, reader)
    }


    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input


        every { useCase.execute(input) } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("No meals in the database.") }
    }

    @Test
    fun `should handle EmptyPatternException`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input
        every { useCase.execute(input) } throws EmptyPatternException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Meal name should not be empty") }
    }

    @Test
    fun `should handle null input`() {
        // Given
        val input = null
        every { reader.readInput() } returns input
        every { useCase.execute(input) }

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is a problem happened when retrieving the data.") }

    }
    @Test
    fun `should handle EmptyMealNameException`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input


        every { useCase.execute(input) } throws EmptyMealNameException()

        // When
        ui.execute()

        // Then
        verify {
            viewer.log("Meal name should not be empty")

        }
    }

    @Test
    fun `should handle EmptyTextException`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input


        every { useCase.execute(input) } throws EmptyTextException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("Empty meal name in database") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input
        every { useCase.execute(input) } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("No meals found matching '$input'.") }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        val input = "Aboud"
        every { reader.readInput() } returns input
        every { useCase.execute(input) } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is a problem happened when retrieving the data.") }
    }


    @Test
    fun `should find a meal when user search by the name`(){
        // Given
        val meals = listOf(createMealHelper(name = "pizza"))
        val input = "pizza"

        every { reader.readInput() } returns input
        every { useCase.execute(input) } returns meals

        // when
        ui.execute()

        // Then

        verify{
            viewer.log("Matching Meals:\n${meals.joinToString("\n") { "- ${it.name} " }}")}
    }
    @Test
    fun `should find a meal when user search by null`(){
        // Given
        val meals = listOf(createMealHelper(name = ""))
        val input = ""

        every { reader.readInput() } returns input
        every { useCase.execute(input) } returns meals

        // when
        ui.execute()

        // Then

        verify{
            viewer.log("Matching Meals:\n${meals.joinToString("\n") { "- ${it.name} " }}")}
    }

}


