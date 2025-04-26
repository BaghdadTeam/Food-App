package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import helpers.suggest.HighCalorieMealTestData
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.logic.usecase.suggest.SuggestHighCalorieMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class HighCalorieMealUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var suggestHighCalorieMealUseCase: SuggestHighCalorieMealUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk(relaxed = true)
        suggestHighCalorieMealUseCase = SuggestHighCalorieMealUseCase(mealsProvider)
    }

    @Test
    fun `return meal with high calorie when found by calorie`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfOneHighCalorieMeal()

        // When
        val result = suggestHighCalorieMealUseCase.execute()

        // Then
        assertThat(result.nutrition?.calories).isGreaterThan(700)
    }

    @Test
    fun `throw an NoMealFoundException when no high calorie meal found`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfOneNotHighCalorieMeal()

        // When & Then
        assertThrows<NoMealFoundException> { suggestHighCalorieMealUseCase.execute() }
    }

    @Test
    fun `should throw an EmptyMealsException when MealsProvider returns empty list`() {
        every { mealsProvider.getMeals() } returns emptyList()

        // When & Then
        assertThrows<EmptyMealsException> { suggestHighCalorieMealUseCase.execute() }
    }

    @Test
    fun `should throw an NoMealFoundException when no high calorie meal left to suggested`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfOneHighCalorieMeal()

        // when
        suggestHighCalorieMealUseCase.execute()

        // Then
        assertThrows<NoMealFoundException> { suggestHighCalorieMealUseCase.execute() }

    }

    @Test
    fun `return different meal when multiple high calorie meals suggested`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfTwoDifferentHighCalorieMeals()

        // when
        suggestHighCalorieMealUseCase.execute()

        // Then
        suggestHighCalorieMealUseCase.execute()
    }

    @Test
    fun `throw an NoMealFoundException when multiple same high calorie meals suggested`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfTwoTheSameHighCalorieMeals()

        // when
        suggestHighCalorieMealUseCase.execute()

        // Then
        assertThrows<NoMealFoundException> { suggestHighCalorieMealUseCase.execute() }
    }

    @Test
    fun `throw an NoMealFoundException when there is only null meal`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfOneNullMeal()

        // When & Then
        assertThrows<NoMealFoundException> { suggestHighCalorieMealUseCase.execute() }
    }
    @Test
    fun `throw an NoMealFoundException when there is meal with null nutrition`() {
        // Given
        every { mealsProvider.getMeals() } returns HighCalorieMealTestData.listOfOneMealHasNutritionNull()

        // When & Then
        assertThrows<NoMealFoundException> { suggestHighCalorieMealUseCase.execute() }
    }



}