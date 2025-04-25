package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import helpers.suggest.EggFreeSweetsTestData
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.logic.usecase.suggest.EggFreeSweetsUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EggFreeSweetsUseCaseTest {

    private lateinit var mealsProvider: MealsProvider
    private lateinit var eggFreeSweetsUseCase: EggFreeSweetsUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk(relaxed = true)
        eggFreeSweetsUseCase = EggFreeSweetsUseCase(mealsProvider)
    }

    @Test
    fun `should return sweet meal that does not contain eggs in the ingredients`() {
        // Given
        every { mealsProvider.getMeals() } returns EggFreeSweetsTestData.allTestMeals()

        // When
        val result = eggFreeSweetsUseCase.execute()

        // Then
        assertThat(result).isIn(EggFreeSweetsTestData.allEggFreeSweets())
    }

    @Test
    fun `should throw EmptyMealsException when no meals are available`() {
        // Given
        every { mealsProvider.getMeals() } returns emptyList()

        // When & Then
        assertThrows<EmptyMealsException> {
            eggFreeSweetsUseCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when no egg-free sweet meals are available`() {
                // Given
        every { mealsProvider.getMeals() } returns EggFreeSweetsTestData.allNonEggFreeSweets()

        // When & Then

        assertThrows<NoMealFoundException> {
            eggFreeSweetsUseCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException if the same meal is already seen`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(EggFreeSweetsTestData.eggFreeCoconutLadoo())

        // First execution to see the meal
        val result = eggFreeSweetsUseCase.execute()
        assertThat(result).isEqualTo(EggFreeSweetsTestData.eggFreeCoconutLadoo())

        // Second execution throws exception
        assertThrows<NoMealFoundException> {
            eggFreeSweetsUseCase.execute()
        }
    }

    @Test
    fun `should handle tags and ingredients with mixed casing and extra spaces`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(EggFreeSweetsTestData.eggFreeCoconutLadoo())

        // When
        val result = eggFreeSweetsUseCase.execute()

        // Then
        assertThat(result.name).isEqualTo(EggFreeSweetsTestData.eggFreeCoconutLadoo().name)
    }

    @Test
    fun `should ignore meals with null tags or null ingredients`() {
        // Given
        every { mealsProvider.getMeals() } returns EggFreeSweetsTestData.nullableMeals()

        // When & Then
        assertThrows<NoMealFoundException> {
            eggFreeSweetsUseCase.execute()
        }
    }
}