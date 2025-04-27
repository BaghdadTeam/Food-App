package presentation.suggest

import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import org.example.logic.usecase.suggest.SuggestKetoMealUseCase
import org.example.presentation.suggest.KetoFriendlyMealUI
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KetoFriendlyMealUITest {
    private lateinit var ketoFriendlyUI: KetoFriendlyMealUI
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var useCase: SuggestKetoMealUseCase

    @BeforeEach
    fun setUp() {
        mockkObject(MealPresenter)
        viewer = mockk(relaxed = true)
        reader = mockk()
        useCase = mockk()
        ketoFriendlyUI = KetoFriendlyMealUI(useCase, viewer, reader)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(MealPresenter)
    }

    @Test
    fun `Should execute useCase execute function as it start`() {
        // Given
        every { useCase.execute() } returns mockk()
        // When
        ketoFriendlyUI.execute()
        // Then
        verify(exactly = 1) { useCase.execute() }

    }

    @Test
    fun `Should log meal name as the useCase execute function return a meal`() {
        // Given
        every { useCase.execute() } returns ketoTestMeal
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { viewer.log(match { it.contains("keto meal") }) }
    }

    @Test
    fun `Should ask user to continue to present meal details or suggest another meal`() {
        // Given
        every { useCase.execute() } returns ketoTestMeal
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { reader.readInput() }
    }

    @Test
    fun `When user wanna to see more details about meal should execute meal presenter`() {
        // Given
        every { useCase.execute() } returns ketoTestMeal
        every { reader.readInput() } returns "y"
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { MealPresenter.printDetails(ketoTestMeal) }

    }

    @Test
    fun `When user wanna to see another  meal should execute useCase execute function`() {
        // Given
        every { useCase.execute() } returns ketoTestMeal
        every { reader.readInput() } returnsMany listOf("n", "y")
        // When
        ketoFriendlyUI.execute()
        // Then
        verify(exactly = 2) { useCase.execute() }

    }

    @Test
    fun `When user enter a invalid input should log 'Invalid input' `() {
        // Given
        every { useCase.execute() } returns ketoTestMeal
        every { reader.readInput() } returnsMany listOf("hello", "45", null, "y")
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { viewer.log(match { it.contains("Invalid input") }) }
    }

    @Test
    fun `When useCase execute function throws EmptyMealsException should log no meals in the database `() {
        // Given
        every { useCase.execute() } throws EmptyMealsException("")
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { viewer.log(match { it.contains("No meals in the database.") }) }
    }

    @Test
    fun `When useCase execute function throws NoMealFoundException should log There is no more unique keto Meals`() {
        // Given
        every { useCase.execute() } throws NoMealFoundException("")
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { viewer.log(match { it.contains("There is no more unique keto Meals") }) }
    }

    @Test
    fun `When useCase execute function throws unknown exception should log There is a problem happened when retrieving the data`() {
        // Given
        every { useCase.execute() } throws Exception("")
        // When
        ketoFriendlyUI.execute()
        // Then
        verify { viewer.log(match { it.contains("There is a problem happened when retrieving the data") }) }
    }


    private val ketoTestMeal = createMealHelper("keto meal")
}