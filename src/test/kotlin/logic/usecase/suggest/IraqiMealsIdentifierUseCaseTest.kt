package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import helpers.suggest.IraqiMealsIdentifierTestData
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.logic.usecase.suggest.IraqiMealsIdentifierUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IraqiMealsIdentifierUseCaseTest {

    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: IraqiMealsIdentifierUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk(relaxed = true)
        useCase = IraqiMealsIdentifierUseCase(mealsProvider)
    }

    @Test
    fun `should return iraqi meals that contains iraqi in the tag or iraq in the description`() {
        // Given
        every { mealsProvider.getMeals() } returns IraqiMealsIdentifierTestData.allMeals()

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactlyElementsIn(IraqiMealsIdentifierTestData.allIraqiMeals())
    }

    @Test
    fun `should return iraqi meals if the description contains iraq and the tag doesn't contain iraqi tag`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.mealWithIraqInDescription())

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactly(IraqiMealsIdentifierTestData.mealWithIraqInDescription())
    }

    @Test
    fun `should return iraqi meal if the iraq or iraqi are inconsistent case`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.iraqiMealWithInconsistentSpaceAndCase())

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactly(IraqiMealsIdentifierTestData.iraqiMealWithInconsistentSpaceAndCase())
    }

    @Test
    fun `should return iraqi meals if the tags contain iraqi tag and description doesn't contain iraq`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.mealWithIraqiTag())

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactly(IraqiMealsIdentifierTestData.mealWithIraqiTag())
    }

    @Test
    fun `should return iraqi meals if the description contains iraq and the tag is null`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.iraqiMealWithNullTags())

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactly(IraqiMealsIdentifierTestData.iraqiMealWithNullTags())

    }

    @Test
    fun `should return iraqi meals if the description is null and the tags contains iraqi tag`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.iraqiMealWithNullDescription())

        // When
        val result = useCase.execute()

        // Then
        assertThat(result).containsExactly(IraqiMealsIdentifierTestData.iraqiMealWithNullDescription())

    }

    @Test
    fun `should throw EmptyMealsException when no meals found from the provider`() {
        // Given
        every { mealsProvider.getMeals() } returns emptyList()

        // When & Then
        assertThrows<EmptyMealsException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when no iraqi meals found`() {
        // Given
        every { mealsProvider.getMeals() } returns IraqiMealsIdentifierTestData.allNonIraqiMeals()

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when both description and tags are null`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.nullMeal())

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when both description and tags are empty`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.emptyMeal())

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException when there is no iraq in the description or Iraqi tag`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(IraqiMealsIdentifierTestData.nonIraqiMeal())

        // When & Then
        assertThrows<NoMealFoundException> {
            useCase.execute()
        }
    }
}