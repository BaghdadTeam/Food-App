import helpers.suggest.HighCalorieMealTestData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.example.logic.usecase.suggest.SuggestHighCalorieMealUseCase
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.presentation.suggest.HighCalorieMealUI
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class HighCalorieMealUITest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var useCase: SuggestHighCalorieMealUseCase
    private lateinit var ui: HighCalorieMealUI

    @BeforeEach
    fun setup() {
        reader = mockk()
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = HighCalorieMealUI(useCase, viewer, reader)
    }

    @Test
    fun `should show high calorie meal and print details when user write y yes`() {
        // Given
        val highCalorieMeal = HighCalorieMealTestData.listOfOneHighCalorieMeal()[0]
        every { useCase.execute() } returns highCalorieMeal
        every { reader.readInput() } returns "y"

        // When
        ui.execute()

        // Then
        verifySequence {
            viewer.log("Meal Name : ${highCalorieMeal.name}")
            viewer.log("Would you like to see the description or get another suggestion? (y/n): ")
        }
    }

    @Test
    fun `should keep show high calorie meal name when user has not write yes or no and print details when user write y yes`() {
        // Given
        val highCalorieMeal = HighCalorieMealTestData.listOfOneHighCalorieMeal()[0]
        every { useCase.execute() } returns highCalorieMeal
        every { reader.readInput() } returnsMany listOf("a", "b", "s", "y")

        // When
        ui.execute()

        // Then
        verify(exactly = 4) { viewer.log("Meal Name : ${highCalorieMeal.name}") }
        verify(exactly = 3) { viewer.log("Invalid input. Please enter 'y' or 'n'.") }
        verify(exactly = 4) { viewer.log(match { it.contains("Would you like to see the description or get another suggestion? (y/n): ") }) }
    }

    @Test
    fun `should handle EmptyMealsException`() {
        // Given
        every { useCase.execute() } throws EmptyMealsException()

        // When
        ui.execute()

        // Then
        verify(exactly = 1) { viewer.log("No meals in the database.") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // Given
        every { useCase.execute() } throws NoMealFoundException()

        // When
        ui.execute()

        // Then
        verify {
            viewer.log(
                """There is no high calories meal at the moment
                |please try again later.
            """.trimMargin()
            )
        }
    }

    @Test
    fun `should handle general Exception`() {
        // Given
        every { useCase.execute() } throws Exception()

        // When
        ui.execute()

        // Then
        verify { viewer.log("There something wrong happened when retrieving the data") }
    }

    @Test
    fun `should handle if the input is null`() {
        // Given
        val highCalorieMeal = HighCalorieMealTestData.listOfOneHighCalorieMeal()[0]
        every { useCase.execute() } returns highCalorieMeal
        every { reader.readInput() } returnsMany listOf(null, "y")

        // When
        ui.execute()

        // Then
        verify(exactly = 2) { viewer.log("Meal Name : ${highCalorieMeal.name}") }
        verify(exactly = 2) { viewer.log(match { it.contains("Would you like to see the description or get another suggestion? (y/n): ") }) }
    }


    @Test
    fun `should keep asking until the answer is yes`() {
        // Given
        val highCalorieMeal = HighCalorieMealTestData.listOfOneHighCalorieMeal()[0]
        every { useCase.execute() } returns highCalorieMeal
        every { reader.readInput() } returnsMany listOf("n", "N", "n", "y")

        // When
        ui.execute()

        // Then
        verify(exactly = 4) { viewer.log("Meal Name : ${highCalorieMeal.name}") }
        verify(exactly = 4) { viewer.log(match { it.contains("Would you like to see the description or get another suggestion? (y/n): ") }) }
    }
}