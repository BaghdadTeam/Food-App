package presentation
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.filter.GymMealHelperUseCase
import model.Meal
import org.example.presentation.Feature
import org.example.presentation.MealTablePrinter
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.example.presentation.GymHelperUI

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
        assertThat(gymHelperUI.id).isEqualTo(9)
        assertThat(gymHelperUI.name).isEqualTo("Gym helper")
    }

    @Test
    fun `should implement Feature interface`() {
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

        // Inject mock printer (you may need to modify GymHelperUI to accept printer in constructor)
        val gymHelperUIWithMockPrinter = GymHelperUI(useCase, viewer, reader) {
            mockPrinter
        }

        // When
        gymHelperUIWithMockPrinter.execute()

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
}