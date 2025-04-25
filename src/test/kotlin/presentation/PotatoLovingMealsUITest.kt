package presentation


import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.usecase.PotatoLovingMealsUseCase
import model.Nutrition
import org.example.presentation.PotatoLovingMealsUI
import org.example.presentation.Viewer
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

        verify { viewer.log("No potato meals found in the Dataset!") }
    }

    @Test
    fun `should log 10 or fewer potato meals with correct format`() {
        val meals = List(12) {
            createMealHelper(
                id = it,
                name = "Potato Dish $it",
                description = "A tasty potato meal number $it",
                nutrition = Nutrition(
                    calories = 100.0 + it,
                    totalFat = 5.0,
                    sugar = 2.0,
                    sodium = 200.0,
                    protein = 3.0,
                    saturatedFat = 1.0,
                    carbohydrates = 15.0
                ),
                ingredients = listOf("potato", "salt", "oil"),
                contributorId = it,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                tags = listOf("vegetarian", "quick"),
                nSteps = 3,
                steps = listOf("Peel", "Fry", "Serve"),
                nIngredients = 3,
                preparationTime = 15
            )
        }

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
        val meals = List(3) {
            createMealHelper(
                id = it,
                name = "Small Potato Dish $it",
                description = "Delicious description $it",
                nutrition = Nutrition(
                    calories = 90.0 + it,
                    totalFat = 4.0,
                    sugar = 1.5,
                    sodium = 180.0,
                    protein = 2.0,
                    saturatedFat = 0.5,
                    carbohydrates = 13.0
                ),
                ingredients = listOf("potato", "oil"),
                contributorId = it,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                tags = listOf("easy"),
                nSteps = 2,
                steps = listOf("Chop", "Boil"),
                nIngredients = 2,
                preparationTime = 10
            )
        }

        every { useCase.execute() } returns meals

        feature.execute()

        verify(exactly = 3) {
            viewer.log(match { it.contains("Small Potato Dish") })
        }
    }

    @Test
    fun `should handle null description, ingredients, and calories gracefully`() {
        val meal = createMealHelper(
            id = 1,
            name = "Mystery Potato",
            description = null,
            nutrition = null,
            ingredients = null,
            contributorId = 0,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            tags = emptyList(),
            nSteps = 0,
            steps = emptyList(),
            nIngredients = 0,
            preparationTime = 0
        )

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
        every { useCase.execute() } throws RuntimeException("Data failure")

        feature.execute()

        verify {
            viewer.log("An error occurred while fetching potato meals: Data failure")
        }
    }
}
