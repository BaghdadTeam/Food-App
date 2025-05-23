package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import helpers.suggest.KetoTestMeals
import model.Meal
import org.example.logic.usecase.suggest.SuggestKetoMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
    fun `should throw NoMealFoundException if the meal already suggested`() {
        // Given
        every { mealProvider.getMeals() } returns listOf(KetoTestMeals.baseKetoMeal())
        // When
        val result = useCase.execute()
        assertThat(result).isEqualTo(KetoTestMeals.baseKetoMeal())
        // Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should skip invalid then return valid keto meal from mixed list`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.invalidKetoMeals() + KetoTestMeals.baseKetoMeal()
        // When
        val result = useCase.execute()
        // Then
        assertThat(result).isEqualTo(KetoTestMeals.baseKetoMeal())
    }

    @Test
    fun `Returns a meal with protein between 10 and 30 when keto meals are available`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.validKetoMeals()
        // When
        val result = useCase.execute()
        assertThat(result.nutrition?.protein).isIn(10..30)
    }

    @Test
    fun `Returns a meal with carbohydrates less than or equal to 10 when keto meals are available`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.validKetoMeals()
        // When
        val result = useCase.execute()
        // Then
        assertThat(result.nutrition?.carbohydrates).isAtMost(10.0)
    }

    @Test
    fun `Returns a meal with sugar less than or equal to 5 when keto meals are available`() {
        //Given
        every { mealProvider.getMeals() } returns KetoTestMeals.validKetoMeals()
        // When
        val result = useCase.execute()
        // Then
        assertThat(result.nutrition?.sugar).isAtMost(5.0)
    }

    @Test
    fun `Returns a meal with total fat greater than or equal to 15 when keto meals are available`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.validKetoMeals()
        // When
        val result = useCase.execute()
        // Then
        assertThat(result.nutrition?.totalFat).isAtLeast(15.0)
    }

    @Test
    fun `Should throw NoMealFoundException if there is no meal match keto meal conditions`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.invalidKetoMeals()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }

    }

    @Test
    fun `Should throw NoMealFoundException if all meals have null nutrition`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.edgeCases()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }

    }
    @Test
    fun `Should throw NoMealFoundException when carbohydrates is null`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.edgeCases()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }
    }

    @Test
    fun `Should throw NoMealFoundException when sugar is null`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.edgeCases()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }
    }

    @Test
    fun `Should throw NoMealFoundException when protein is null`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.edgeCases()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }

    }

    @Test
    fun `Should throw NoMealFoundException when total fat  is null`() {
        // Given
        every { mealProvider.getMeals() } returns KetoTestMeals.ketoMealsWithNullFat()
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }

    }
    @Test
    fun `Should throw NoMealFoundException when nutrition is null`() {
        // Given
        every { mealProvider.getMeals() } returns listOf(KetoTestMeals.nullNutritionMeal())
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }
    }
    @Test
    fun `Should throw NoMealFoundException when all meals already suggest`(){
        // Given
        val allValidMeals = KetoTestMeals.validKetoMeals()
        every { mealProvider.getMeals() } returns allValidMeals
        repeat(allValidMeals.size-1){useCase.execute()}
        //When & Then
        assertThrows<NoMealFoundException> { useCase.execute() }
    }
}