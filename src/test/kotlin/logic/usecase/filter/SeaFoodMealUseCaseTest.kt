package logic.usecase.filter

import com.google.common.truth.Truth.assertThat
import helpers.filter.SeaFoodTestData
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.logic.usecase.filter.SeaFoodMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SeaFoodMealUseCaseTest {

    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: SeaFoodMealUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk(relaxed = true)
        useCase = SeaFoodMealUseCase(mealsProvider)
    }

    @Test
    fun `should return sorted seafood meals by protein`() {

        every { mealsProvider.getMeals() } returns SeaFoodTestData.allMeals()

        // when
        val result = useCase.execute()

        // then
        assertThat(result[0].second).isEqualTo(SeaFoodTestData.seaFoodMeals()[0])
        assertThat(result[1].second).isEqualTo(SeaFoodTestData.seaFoodMeals()[1])
    }

    @Test
    fun `should throw NoMealFoundException when the tag is null`() {
        // Given
        every { mealsProvider.getMeals() } returns SeaFoodTestData.mealWithNullTag()

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when the nutrition is null`() {
        // Given
        every { mealsProvider.getMeals() } returns SeaFoodTestData.seaFoodMealWithNullNutrition()

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when the protein is null`() {
        // Given
        every { mealsProvider.getMeals() } returns SeaFoodTestData.seaFoodMealWithNullProtein()

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw EmptyMealsException when no meals are available`() {
        // given
        every { mealsProvider.getMeals() } returns emptyList()

        // when & then
        assertThrows<EmptyMealsException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when no seafood meals are found`() {
        // given
        every { mealsProvider.getMeals() } returns SeaFoodTestData.nonSeaFoodMeals()

        // when & then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }
}