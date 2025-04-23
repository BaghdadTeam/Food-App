package presentation
import io.mockk.*
import helpers.createMealHelper
import logic.usecase.FilterQuickHealthyMealsUseCase
import org.example.presentation.QuickHealthyMealsUI
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class QuickHealthyMealsUITest {
    private lateinit var useCase: FilterQuickHealthyMealsUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var quickHealthyMealsUI: QuickHealthyMealsUI

    @BeforeEach
    fun setUp() {
        reader = mockk()
        viewer = mockk(relaxed = true)
        useCase = mockk()
        quickHealthyMealsUI = QuickHealthyMealsUI(useCase, viewer, reader)
    }
    @Test
    fun `Should log Please enter a valid positive number if input is null`() {
        // Given
        every { reader.readInput() } returns null
        // When
        quickHealthyMealsUI.execute()
        // Then
        verifySequence {
            viewer.log("Please enter the number of meals you want to see:")
            viewer.log("Please enter a valid positive number.")
        }
    }
    @ParameterizedTest
    @CsvSource( "a","0", "-2","000")
    fun `should log 'Please enter a valid number' if input can't be integer or less or equal to zero`(input: String?) {
        // Given
        every { reader.readInput() } returns input
        // When
        quickHealthyMealsUI.execute()
        // Then
        verifySequence {
            viewer.log("Please enter the number of meals you want to see:")
            viewer.log("Please enter a valid positive number.")
        }
    }
    @ParameterizedTest
    @CsvSource("  3  ", "3")
    fun `Should execute use case execute function if the input is valid integer `(input: String?) {
        // Given
        every { reader.readInput() } returns input
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify{useCase.execute(input?.toInt() ?:1 ) }

    }

    @Test
    fun `Should execute the use case with the exact count input from user  `() {
        // Given
        every { reader.readInput() } returns "3"
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify { useCase.execute(3) }
    }

    @Test
    fun `Should log 'No quick healthy meals found'  if there is no meals`() {
        // Given
        every { reader.readInput() }  returns "1"
        every { useCase.execute(1) } returns emptyList()
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify {viewer.log(match {it.contains("No quick healthy meals found" )})}
    }

    @Test
    fun `Should log quick & healthy meal names if there are meals`() {
        // Given
        every { reader.readInput() } returns "1"
        every { useCase.execute(1) } returns listOf(
            createMealHelper(
                name = "Healthy Meal",
            )
        )
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify { viewer.log(match { it.contains("Healthy Meal") }) }
    }
    @Test
    fun `Should log 'There is no meals in database' if the use case throw EmptyMealsException `(){
        // Given
        every { reader.readInput() } returns "3"
        every { useCase.execute(3) } throws EmptyMealsException("")
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify { viewer.log(match { it.contains("There is no meals in database") }) }

    }

    @Test
    fun `Should log 'There are no quick healthy meals available ' if the use case throw NoMealFoundException `(){
        // Given
        every { reader.readInput() } returns "3"
        every { useCase.execute(3) } throws NoMealFoundException("")
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify { viewer.log(match { it.contains("There are no quick healthy meals available ") }) }

    }
    @Test
    fun `Should log 'There is a problem happened when retrieving the data' if use case throws any other exception `(){
        // Given
        every { reader.readInput() } returns "3"
        every { useCase.execute(3) } throws Exception("")
        // When
        quickHealthyMealsUI.execute()
        // Then
        verify { viewer.log(match { it.contains("There is a problem happened when retrieving the data.") }) }

    }
}