package presentation.filter

import helpers.createMealHelper
import helpers.createNutritionHelper
import helpers.filter.SeaFoodTestData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.usecase.filter.SeaFoodMealUseCase
import model.Meal
import org.example.presentation.Viewer
import org.example.presentation.filter.SeafoodMealsUI
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Future

class SeafoodMealsUITest {

    private lateinit var viewer: Viewer
    private lateinit var useCase: SeaFoodMealUseCase
    private lateinit var ui: SeafoodMealsUI

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        useCase = mockk()
        ui = SeafoodMealsUI(useCase, viewer)
    }

    @Test
    fun `should display ranked seafood meals`() {
        // given
        every { useCase.execute() } returns SeaFoodTestData.rankedSeaFoodMeals()

        val ui = SeafoodMealsUI(useCase, viewer)

        // when
        ui.execute()

        // then
        verify { viewer.log("Rank    Name    Protein") }
        verify { viewer.log("1      Shrimp Pasta     30.0") }
        verify { viewer.log("2      Grilled Salmon     20.0") }
    }

    @Test
    fun `should handle EmptyMealsException`() {
        // given
        every { useCase.execute() } throws EmptyMealsException("No meals found")

        val ui = SeafoodMealsUI(useCase, viewer)

        // when
        ui.execute()

        // then
        verify { viewer.log("There is no meals in database") }
    }

    @Test
    fun `should handle NoMealFoundException`() {
        // given
        every { useCase.execute() } throws NoMealFoundException("There is no Sea Food Meals")

        val ui = SeafoodMealsUI(useCase, viewer)

        // when
        ui.execute()

        // then
        verify { viewer.log("There is no seafood meals found") }
    }

    @Test
    fun `should handle general Exception`() {
        // given
        every { useCase.execute() } throws Exception("Something went wrong")

        val ui = SeafoodMealsUI(useCase, viewer)

        // when
        ui.execute()

        // then
        verify { viewer.log("There is a problem happened when retrieving the data.") }
    }
}