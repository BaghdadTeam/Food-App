package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.utils.EmptyMealsException

import org.junit.jupiter.api.*

class SuggestKetoMealUseCaseTest {
    private lateinit var mealProvider: MealsProvider
    private lateinit var useCase: SuggestKetoMealUseCase


    @BeforeEach
    fun setUp() {
        mealProvider = mockk(relaxed = true)
        useCase = SuggestKetoMealUseCase(mealProvider)
    }
    @Test
    fun `Should throw EmptyMealsException when there is no meals to filler`() {
        // Given
        every { mealProvider.getMeals() }.returns(emptyList())
        // When & Then
        assertThrows<EmptyMealsException> { useCase.execute() }
    }
}