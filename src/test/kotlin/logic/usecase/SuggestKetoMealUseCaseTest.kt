package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import logic.helpers.createMealHelper
import model.Meal
import model.Nutrition
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

import org.junit.jupiter.api.*

class SuggestKetoMealUseCaseTest {
    private lateinit var mealProvider: MealsProvider
    private lateinit var useCase: SuggestKetoMealUseCase


    @BeforeEach
    fun setUp() {
        mealProvider = mockk()
        useCase = SuggestKetoMealUseCase(mealProvider)
    }

    @Test
    fun `Should throw EmptyMealsException when there is no meals to filler`() {
        // Given
        every { mealProvider.getMeals() }.returns(emptyList())
        // When & Then
        assertThrows<EmptyMealsException> { useCase.execute() }
    }

    @Test
    fun `Should return random meal if there is meal match keto meal conditions`() {
        every { mealProvider.getMeals() } returns listOf(
            createMealHelper(
                nutrition = Nutrition(
                    totalFat = 20.0,
                    calories = null,
                    sugar = 4.0,
                    sodium = null,
                    protein = 20.0,
                    saturatedFat = null,
                    carbohydrates = 5.0,
                )

            ), createMealHelper(
                nutrition = Nutrition(
                    totalFat = 20.0,
                    calories = null,
                    sugar = 4.0,
                    sodium = null,
                    protein = 40.0,
                    saturatedFat = null,
                    carbohydrates = 5.0,
                )
            ), createMealHelper(
                nutrition = Nutrition(
                    totalFat = 20.0,
                    calories = null,
                    sugar = 4.0,
                    sodium = null,
                    protein = 10.0,
                    saturatedFat = null,
                    carbohydrates = 5.0,
                )

            )
        )
        val result = useCase.execute()
        assertThat(result.nutrition?.protein).isIn(10..30)

    }

    @Test
    fun `Should throw NoMealFoundException if there is no meal match keto meal conditions`() {
        every { mealProvider.getMeals() } returns (listOf(
            createMealHelper(
                nutrition = Nutrition(
                    totalFat = 5.0,
                    calories = null,
                    sugar = 6.0,
                    sodium = null,
                    protein = 40.0,
                    saturatedFat = null,
                    carbohydrates = 50.0,
                )
            )
        ))

        assertThrows<NoMealFoundException> { useCase.execute() }

    }
    @Test
    fun `Should throw NoMealFoundException if only one meal and have null nutrition info and  no other meals to match keto meal conditions`() {
        every { mealProvider.getMeals() } returns (listOf(
            createMealHelper(
                nutrition = Nutrition(
                    totalFat = null,
                    calories = null,
                    sugar = null,
                    sodium = null,
                    protein = null,
                    saturatedFat = null,
                    carbohydrates = null,
                )
            )
        ))

        assertThrows<NoMealFoundException> { useCase.execute() }

    }
}