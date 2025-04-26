package logic.usecase.filter

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import model.Nutrition
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GymMealHelperUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var gymMealHelper: GymMealHelperUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk()
        gymMealHelper = GymMealHelperUseCase(mealsProvider)
    }

    @Test
    fun `throw EmptyMealsException when provider empty`() {
        // Given
        every { mealsProvider.getMeals() } returns emptyList()
        // When & Then
        assertThrows<EmptyMealsException> {
            gymMealHelper.getGymMealsSuggestion()
        }
    }

    @Test
    fun `throw NoMealFoundException when only calories out of range`() {
        // Given
        val tooHighCalories = createMealHelper(
            name = "calories off",
            nutrition = createFullNutrition(calories = 600.0, protein = 20.0)
        )
        every { mealsProvider.getMeals() } returns listOf(tooHighCalories)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0)
        }
    }

    @Test
    fun `throw NoMealFoundException when only protein out of range`() {
        // Given
        val tooHighProtein = createMealHelper(
            name = "protein off",
            nutrition = createFullNutrition(calories = 200.0, protein = 40.0)
        )
        every { mealsProvider.getMeals() } returns listOf(tooHighProtein)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetProtein = 30.0)
        }
    }

    @Test
    fun `return meal when only calories in range`() {
        // Given
        val caloriesInRange = createMealHelper(
            name = "only calories in range",
            nutrition = createFullNutrition(calories = 450.0, protein = 5.0)
        )
        every { mealsProvider.getMeals() } returns listOf(caloriesInRange)
        // When
        val result = gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0)
        // Then
        assertThat(result).containsExactly(caloriesInRange)
    }

    @Test
    fun `return meal when only protein in range`() {
        // Given
        val proteinInRange = createMealHelper(
            name = "only protein in range",
            nutrition = createFullNutrition(calories = 150.0, protein = 28.0)
        )
        every { mealsProvider.getMeals() } returns listOf(proteinInRange)
        // When
        val result = gymMealHelper.getGymMealsSuggestion(targetProtein = 30.0)
        // Then
        assertThat(result).containsExactly(proteinInRange)
    }

    @Test
    fun `return meal when both targets in range`() {
        // Given
        val gymMeal = createMealHelper(
            name = "calorie and protein Good",
            nutrition = createFullNutrition(calories = 520.0, protein = 32.0)
        )
        every { mealsProvider.getMeals() } returns listOf(gymMeal)
        // When
        val result = gymMealHelper.getGymMealsSuggestion(500.0, 30.0)
        // Then
        assertThat(result).containsExactly(gymMeal)
    }

    @Test
    fun `return all meals when no targets specified`() {
        // Given
        val firstMeal  = createMealHelper(name = "pizza", nutrition = createFullNutrition(300.0, 20.0))
        val secondMeal = createMealHelper(name = "kebab", nutrition = createFullNutrition(400.0, 25.0))
        every { mealsProvider.getMeals() } returns listOf(firstMeal, secondMeal)
        // When
        val result = gymMealHelper.getGymMealsSuggestion()
        // Then
        assertThat(result).containsExactly(firstMeal, secondMeal)
    }

    @Test
    fun `exclude meals with null nutrition when targets given`() {
        // Given
        val nutritionInTarget = createMealHelper(name = "steak", nutrition = createFullNutrition(500.0, 30.0))
        val nutritionNull     = createMealHelper(name = "no nutrition", nutrition = null)
        every { mealsProvider.getMeals() } returns listOf(nutritionNull, nutritionInTarget)
        // When
        val result = gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0)
        // Then
        assertThat(result).containsExactly(nutritionInTarget)
    }

    @Test
    fun `should throw NoMealFoundException when only calories target and calories null`() {
        // Given
        val mealWithNoCalories = createMealHelper(
            name = "No calories",
            nutrition = createFullNutrition(calories = null, protein = 20.0)
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithNoCalories)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetCalories = 100.0)
        }
    }

    @Test
    fun `should throw NoMealFoundException when only protein target and protein null`() {
        // Given
        val mealWithNoProtein = createMealHelper(
            name = "No protein",
            nutrition = createFullNutrition(calories = 200.0, protein = null)
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithNoProtein)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetProtein = 30.0)
        }
    }

    @ParameterizedTest(name = "calories={1}, expect inRange={2}")
    @CsvSource(
        "500.0, 450.0, true",
        "500.0, 550.0, true",
        "500.0, 449.9, false",
        "500.0, 550.1, false"
    )
    fun `calories boundary test`(targetCalories: Double, mealCalories: Double, expected: Boolean) {
        // Given
        val meal = createMealHelper(
            name = "Test",
            nutrition = createFullNutrition(calories = mealCalories, protein = 10.0)
        )
        every { mealsProvider.getMeals() } returns listOf(meal)

        if (expected) {
            // When
            val result = gymMealHelper.getGymMealsSuggestion(targetCalories = targetCalories)
            // Then
            assertThat(result).containsExactly(meal)
        } else {
            // When & Then
            assertThrows<NoMealFoundException> {
                gymMealHelper.getGymMealsSuggestion(targetCalories = targetCalories)
            }
        }
    }

    @ParameterizedTest(name = "protein={1}, expect inRange={2}")
    @CsvSource(
        "30.0, 25.0, true",
        "30.0, 35.0, true",
        "30.0, 24.9, false",
        "30.0, 35.1, false"
    )
    fun `protein boundary test`(targetProtein: Double, mealProtein: Double, expected: Boolean) {
        // Given
        val meal = createMealHelper(
            name = "Test",
            nutrition = createFullNutrition(calories = 200.0, protein = mealProtein)
        )
        every { mealsProvider.getMeals() } returns listOf(meal)

        if (expected) {
            // When
            val result = gymMealHelper.getGymMealsSuggestion(targetProtein = targetProtein)
            // Then
            assertThat(result).containsExactly(meal)
        } else {
            // When & Then
            assertThrows<NoMealFoundException> {
                gymMealHelper.getGymMealsSuggestion(targetProtein = targetProtein)
            }
        }
    }

    @Test
    fun `both targets greater than zero but only calories match yields no match`() {
        // Given
        val meal = createMealHelper(
            name = "CalsOnly",
            nutrition = createFullNutrition(calories = 100.0, protein = 1000.0)
        )
        every { mealsProvider.getMeals() } returns listOf(meal)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetCalories = 100.0, targetProtein = 50.0)
        }
    }

    @Test
    fun `both targets greater than zero but only protein match yields no match`() {
        // Given
        val meal = createMealHelper(
            name = "ProteinOnly",
            nutrition = createFullNutrition(calories = 1000.0, protein = 50.0)
        )
        every { mealsProvider.getMeals() } returns listOf(meal)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion(targetCalories = 100.0, targetProtein = 50.0)
        }
    }

    @Test
    fun `should exclude meals with null nutrition when no targets specified`() {
        // Given
        val nutritionNoTarget = createMealHelper(name = "good meal", nutrition = createFullNutrition(300.0, 20.0))
        val nutritionNull     = createMealHelper(name = "bad meal",  nutrition = null)
        every { mealsProvider.getMeals() } returns listOf(nutritionNull, nutritionNoTarget)
        // When
        val result = gymMealHelper.getGymMealsSuggestion()
        // Then
        assertThat(result).containsExactly(nutritionNoTarget)
    }

    @Test
    fun `should exclude meals with blank or null name when no targets specified`() {
        // Given
        val nameBlank = createMealHelper(name = "",  nutrition = createFullNutrition(300.0, 20.0))
        val nameNull  = createMealHelper(name = null,nutrition = createFullNutrition(300.0, 20.0))
        every { mealsProvider.getMeals() } returns listOf(nameBlank, nameNull)
        // When & Then
        assertThrows<NoMealFoundException> {
            gymMealHelper.getGymMealsSuggestion()
        }
    }

    private fun createFullNutrition(
        calories: Double? = null,
        protein: Double? = null
    ): Nutrition = Nutrition(
        calories = calories,
        protein = protein,
        totalFat = null, sugar = null,
        sodium = null, saturatedFat = null, carbohydrates = null
    )
}
