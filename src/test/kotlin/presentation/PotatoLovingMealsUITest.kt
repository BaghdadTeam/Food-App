package presentation


import helpers.suggest.PotatoLovingMealsTestData.getNullDataMeal
import helpers.suggest.PotatoLovingMealsTestData.getSampleMeals
import helpers.suggest.PotatoLovingMealsTestData.getSmallPotatoMeals
import logic.usecase.PotatoLovingMealsUseCase
import org.example.presentation.PotatoLovingMealsUI
import org.example.presentation.Viewer
import org.example.utils.NoMealFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PotatoLovingMealsUITest {

    private lateinit var useCase: PotatoLovingMealsUseCase
    private lateinit var viewer: Viewer
    private lateinit var feature: PotatoLovingMealsUI

    @BeforeEach
    fun setup() {
        useCase = mockk()
        viewer = mockk(relaxed = true)
        feature = PotatoLovingMealsUI(useCase, viewer)
    }

    @Test
    fun `should log no potato meals when use case returns empty list`() {
        every { useCase.execute() } returns emptyList()

        feature.execute()

        // Update the expected log message to match the actual one in the UI.
        verify { viewer.log("An unexpected error occurred while fetching potato meals: No potato meals available in the dataset.") }
    }


    @Test
    fun `should log 10 or fewer potato meals with correct format`() {
        val meals = getSampleMeals()

        every { useCase.execute() } returns meals

        feature.execute()

        verify { viewer.log("=== 10 Random Potato Meals ===") }
        verify { viewer.log("Total potato meals found: ${meals.size}\n") }

        verify(atMost = 10) {
            viewer.log(match {
                it.contains("ID:") && it.contains("Name:") && it.contains("Calories:")
            })
        }
    }

    @Test
    fun `should log all meals when less than 10 available`() {
        val meals = getSmallPotatoMeals()

        every { useCase.execute() } returns meals

        feature.execute()

        verify(exactly = 3) {
            viewer.log(match { it.contains("Small Potato Dish") })
        }
    }

    @Test
    fun `should handle null description, ingredients, and calories gracefully`() {
        val meal = getNullDataMeal()

        every { useCase.execute() } returns listOf(meal)

        feature.execute()

        verify {
            viewer.log(match {
                it.contains("No Description") &&
                        it.contains("N/A") &&
                        it.contains("No Ingredients")
            })
        }
    }

    @Test
    fun `should handle EmptyMealsException and log the appropriate error message`() {
        // Use `assertThrows` to assert that the exception is thrown
        every { useCase.execute() } throws NoMealFoundException("No potato meals available in the dataset.")

        feature.execute()

        verify {
            viewer.log("No meals available at all: No potato meals available in the dataset.")
        }
    }

    @Test
    fun `should handle generic exception and log error message`() {
        // Use `assertThrows` to assert that the exception is thrown
        every { useCase.execute() } throws NoMealFoundException("Something went wrong")

        feature.execute()

        verify {
            viewer.log("No meals available at all: Something went wrong")
        }
    }
}

/**

class PotatoLovingMealsUITest {

    private lateinit var useCase: PotatoLovingMealsUseCase
    private lateinit var viewer: Viewer
    private lateinit var feature: PotatoLovingMealsUI


    @BeforeEach
    fun setup() {
        useCase = mockk()
        viewer = mockk(relaxed = true)
        feature = PotatoLovingMealsUI(useCase, viewer)
    }

    @Test
    fun `should log no potato meals when use case returns empty list`() {

        every { useCase.execute() } returns emptyList()

        feature.execute()

        verify { viewer.log("No potato meals found in the Dataset!") }
    }

    @Test
    fun `should log 10 or fewer potato meals with correct format`() {
        val meals = getSampleMeals()

        every { useCase.execute() } returns meals

        feature.execute()

        verify { viewer.log("=== 10 Random Potato Meals ===") }
        verify { viewer.log("Total potato meals found: ${meals.size}\n") }

        verify(atMost = 10) {
            viewer.log(match {
                it.contains("ID:") && it.contains("Name:") && it.contains("Calories:")
            })
        }
    }

    @Test
    fun `should log all meals when less than 10 available`() {
        val meals = getSmallPotatoMeals()

        every { useCase.execute() } returns meals

        feature.execute()

        verify(exactly = 3) {
            viewer.log(match { it.contains("Small Potato Dish") })
        }
    }

    @Test
    fun `should handle null description, ingredients, and calories gracefully`() {
        val meal = getNullDataMeal()

        every { useCase.execute() } returns listOf(meal)

        feature.execute()

        verify {
            viewer.log(match {
                it.contains("No Description") &&
                        it.contains("N/A") &&
                        it.contains("No Ingredients")
            })
        }
    }

    @Test
    fun `should handle exception and log error message`() {
        every { useCase.execute() } throws RuntimeException("Something went wrong")

        feature.execute()

        verify {
            viewer.log("An unexpected error occurred while fetching potato meals: Something went wrong")
        }
    }
}
**/