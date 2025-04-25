package logic.usecase.filter

import helpers.createMealHelper
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import logic.usecase.MealsForLargeGroupUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MealsForLargeGroupUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: MealsForLargeGroupUseCase

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk()
        useCase = MealsForLargeGroupUseCase(mealsProvider)
    }

    @Test
    fun `should return EmptyMealsException when no data in csv`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf()

        // When & Then
        assertThrows<EmptyMealsException> {
            useCase.execute()
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
            useCase.execute()
        }
    }

    @Test
    fun `should return meals when found italian meals for large group`() {
        // Given

        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for large group"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for large group"),
        )

        // When
        val meals = useCase.execute()

        // Then
        assertThat(meals).hasSize(2)
        assertThat(meals.map { it.name }).containsExactly("Italian salad dish", "salad dish")
    }



    @Test
    fun `should return meals when found meals for large group`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "Italian salad dish", description = "Italian dish for large group"),
            createMealHelper(name = "Indian salad dish", description = "indian dish for families"),
            createMealHelper(name = "salad dish", description = "Italian dish for large group"),
            createMealHelper(name = "Italian dish", tags = listOf("indian dish for for large group")),
        )

        // When
        val meals = useCase.execute()

        // Then
        assertThat(meals).hasSize(3)
        assertThat(meals.map { it.name }).containsExactly("Italian salad dish", "Italian dish", "salad dish")
    }

    @Test
    fun `should return meal when name is null but tags contain country and query`() {
        // Given
        val matchingMeal = createMealHelper(
            name = null,
            tags = listOf("Italian", "Large group"),
            description = null
        )
        every { mealsProvider.getMeals() } returns listOf(
            matchingMeal,
            createMealHelper(name = "Random dish", description = "Nothing relevant"),
        )

        // When
        val meals = useCase.execute()

        // Then
        assertThat(meals).hasSize(1)
        assertThat(meals[0]).isEqualTo(matchingMeal)
    }

    @Test
    fun `should throw NoMealFoundException when name tags and description are all null`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = null, tags = null, description = null),
        )

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }
}