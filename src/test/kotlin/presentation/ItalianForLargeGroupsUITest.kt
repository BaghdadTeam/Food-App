package presentation

import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.MealsForLargeGroupUseCase
import org.example.presentation.ItalianForLargeGroupsUI
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ItalianForLargeGroupsUITest {

    private lateinit var viewer: Viewer
    private lateinit var useCase: MealsForLargeGroupUseCase
    private lateinit var ui: ItalianForLargeGroupsUI


    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = ItalianForLargeGroupsUI(useCase, viewer)
    }

    @Test
    fun `should return meals and print the name of the meals`() {
        // Given
        val italianMeals = listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for large group"),
            createMealHelper(name = "Italian dish", tags = listOf("indian dish for for large group")),
        )

        every { useCase.execute() } returns italianMeals

        // When
        ui.execute()

        // Then
        verify { viewer.log("Matching Meals:") }
        verify { viewer.log("1 - Italian salad dish") }
        verify { viewer.log("2 - Italian dish") }

    }

    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        every { useCase.execute() } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("No meals in the database.") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        every { useCase.execute() } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify { viewer.log("""There is no meals for large group at the moment
                |please try again later
            """.trimMargin()
        ) }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        every { useCase.execute() } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There is something happened when retrieving data") }
    }

}