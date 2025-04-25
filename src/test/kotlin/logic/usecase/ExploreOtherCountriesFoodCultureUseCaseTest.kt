package logic.usecase

import com.google.common.truth.Truth
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExploreOtherCountriesFoodCultureUseCaseTest{

    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: ExploreOtherCountriesFoodCultureUseCase

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk()
        useCase = ExploreOtherCountriesFoodCultureUseCase(mealsProvider)
    }

    @Test
    fun `should return EmptyMealsException when no data in csv`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf()

        // When & Then
        assertThrows<EmptyMealsException> {
            useCase.execute("Japan")
        }
    }


    @Test
    fun `should return NoMealFoundException when no such meal in the data`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for families"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for small group"),
            createMealHelper(name = "Italian dish", tags = listOf("indian dish for for one person")),
        )

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute("french")
        }
    }


    @Test
    fun `should return meals when country name in meal name or tag or description`(){
        // Given
        val mealsData = listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for families"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for small group"),
            createMealHelper(name = "Italian dish", tags = listOf("indian dish for for one person")),
        )
        every { mealsProvider.getMeals() } returns mealsData

        // When
        val meals = useCase.execute("Italian")

        // Then
        Truth.assertThat(meals).hasSize(3)
        Truth.assertThat(meals).containsExactlyElementsIn(mealsData)
    }

    @Test
    fun `should return meals when country name in meal name`(){
        // Given
        val mealsData = listOf(
            createMealHelper(name = "Italian salad dish", description = " dish for families"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = " dish for small group"),
            createMealHelper(name = "Italian dish", tags = listOf(" dish for for one person")),
        )
        every { mealsProvider.getMeals() } returns mealsData

        // When
        val meals = useCase.execute("Italian")

        // Then
        Truth.assertThat(meals).hasSize(3)
        Truth.assertThat(meals).containsExactlyElementsIn(mealsData)
    }

    @Test
    fun `should return meals when country name in meal description`(){
        // Given
        val mealsData = listOf(
            createMealHelper(name = "salad dish", description = "Italian dish for families"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for small group"),
            createMealHelper(name = "dish", tags = listOf("indian dish for for one person")),
        )
        every { mealsProvider.getMeals() } returns mealsData

        // When
        val meals = useCase.execute("Italian")

        // Then
        Truth.assertThat(meals).hasSize(2)
        Truth.assertThat(meals).containsExactlyElementsIn(mealsData)
    }

    @Test
    fun `should return meals when country name in meal tags`(){
        // Given
        val mealsData = listOf(
            createMealHelper(name = "salad dish", description = "Italian dish for families"),
            createMealHelper(name = "Indian salad dish", tags = listOf("")),
            createMealHelper(name = "salad dish"),
            createMealHelper(name = "dish", tags = listOf("indian dish for for one person")),
        )
        every { mealsProvider.getMeals() } returns mealsData

        // When
        val meals = useCase.execute("indian")

        // Then
        Truth.assertThat(meals).hasSize(2)
        Truth.assertThat(meals).containsExactlyElementsIn(mealsData)
    }

}