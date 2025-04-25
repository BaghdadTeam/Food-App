package logic.usecase

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import logic.usecase.filter.GymMealHelperUseCase
import model.Nutrition
import org.example.utils.EmptyMealNameException
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GymMealHelperUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var gymMealHelper: GymMealHelperUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk()
        gymMealHelper = GymMealHelperUseCase(mealsProvider)
    }

    private fun createFullNutrition(
        calories: Double? = null,
        protein: Double? = null,
        totalFat: Double? = null,
        sugar: Double? = null,
        sodium: Double? = null,
        saturatedFat: Double? = null,
        carbohydrates: Double? = null
    ): Nutrition {
        return Nutrition(
            calories = calories,
            protein = protein,
            totalFat = totalFat,
            sugar = sugar,
            sodium = sodium,
            saturatedFat = saturatedFat,
            carbohydrates = carbohydrates
        )
    }

    @Test
    fun `should throw EmptyMealsException when no meals available`() {
        every { mealsProvider.getMeals() } returns emptyList()
        assertThrows<EmptyMealsException> { gymMealHelper.getGymMealsSuggestion() }
    }

    @Test
    fun `should throw EmptyMealNameException when meal has empty name`() {
        val mealWithEmptyName = createMealHelper(
            name = "",
            nutrition = createFullNutrition(calories = 500.0, protein = 30.0)
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithEmptyName)
        assertThrows<EmptyMealNameException> { gymMealHelper.getGymMealsSuggestion() }
    }

    @Test
    fun `should throw NoMealFoundException when no meals match criteria`() {
        val highCalorieMeal = createMealHelper(
            name = "High Calorie",
            nutrition = createFullNutrition(calories = 1000.0, protein = 50.0)
        )
        every { mealsProvider.getMeals() } returns listOf(highCalorieMeal)
        assertThrows<NoMealFoundException> { gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0) }
    }

    @Test
    fun `should return meals matching target calories`() {
        val matchingMeal = createMealHelper(
            name = "Perfect Meal",
            nutrition = createFullNutrition(calories = 500.0, protein = 30.0)
        )
        val notMatchingMeal = createMealHelper(
            name = "High Calorie",
            nutrition = createFullNutrition(calories = 700.0, protein = 40.0)
        )
        every { mealsProvider.getMeals() } returns listOf(matchingMeal, notMatchingMeal)

        val result = gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0)
        assertThat(result).containsExactly(matchingMeal)
    }

    @Test
    fun `should return meals matching target protein`() {
        val matchingMeal = createMealHelper(
            name = "High Protein",
            nutrition = createFullNutrition(calories = 400.0, protein = 30.0)
        )
        val notMatchingMeal = createMealHelper(
            name = "Low Protein",
            nutrition = createFullNutrition(calories = 400.0, protein = 10.0)
        )
        every { mealsProvider.getMeals() } returns listOf(matchingMeal, notMatchingMeal)

        val result = gymMealHelper.getGymMealsSuggestion(targetProtein = 30.0)
        assertThat(result).containsExactly(matchingMeal)
    }

    @Test
    fun `should return meals matching both calories and protein`() {
        val perfectMeal = createMealHelper(
            name = "Perfect Meal",
            nutrition = createFullNutrition(calories = 500.0, protein = 30.0)
        )
        val onlyCaloriesMatch = createMealHelper(
            name = "Only Calories",
            nutrition = createFullNutrition(calories = 500.0, protein = 10.0)
        )
        val onlyProteinMatch = createMealHelper(
            name = "Only Protein",
            nutrition = createFullNutrition(calories = 700.0, protein = 30.0)
        )
        every { mealsProvider.getMeals() } returns listOf(perfectMeal, onlyCaloriesMatch, onlyProteinMatch)

        val result = gymMealHelper.getGymMealsSuggestion(
            targetCalories = 500.0,
            targetProtein = 30.0
        )
        assertThat(result).containsExactly(perfectMeal)
    }

    @Test
    fun `should return all meals when no targets specified`() {
        val meal1 = createMealHelper(
            name = "Meal 1",
            nutrition = createFullNutrition(calories = 300.0, protein = 20.0)
        )
        val meal2 = createMealHelper(
            name = "Meal 2",
            nutrition = createFullNutrition(calories = 400.0, protein = 25.0)
        )
        every { mealsProvider.getMeals() } returns listOf(meal1, meal2)

        val result = gymMealHelper.getGymMealsSuggestion()
        assertThat(result).containsExactly(meal1, meal2)
    }

    @Test
    fun `should exclude meals without nutrition when targets specified`() {
        val mealWithNutrition = createMealHelper(
            name = "With Nutrition",
            nutrition = createFullNutrition(calories = 500.0, protein = 30.0)
        )
        val mealWithoutNutrition = createMealHelper(name = "No Nutrition")
        every { mealsProvider.getMeals() } returns listOf(mealWithNutrition, mealWithoutNutrition)

        val result = gymMealHelper.getGymMealsSuggestion(targetCalories = 500.0)
        assertThat(result).containsExactly(mealWithNutrition)
    }

    @Test
    fun `should handle edge cases in nutrition ranges`() {
        val edgeCaseMeal = createMealHelper(
            name = "Edge Case",
            nutrition = createFullNutrition(
                calories = 500.0 + GymMealHelperUseCase.Companion.RANGE_CALORIES,
                protein = 30.0 + GymMealHelperUseCase.Companion.RANGE_PROTEIN
            )
        )
        every { mealsProvider.getMeals() } returns listOf(edgeCaseMeal)

        val result = gymMealHelper.getGymMealsSuggestion(
            targetCalories = 500.0,
            targetProtein = 30.0
        )
        assertThat(result).containsExactly(edgeCaseMeal)
    }

    @Test
    fun `should ignore other nutrition elements when filtering`() {
        val mealWithFullNutrition = createMealHelper(
            name = "Full Nutrition Meal",
            nutrition = createFullNutrition(
                calories = 500.0,
                protein = 30.0,
                totalFat = 20.0,
                sugar = 10.0,
                sodium = 5.0,
                saturatedFat = 8.0,
                carbohydrates = 45.0
            )
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithFullNutrition)

        val result = gymMealHelper.getGymMealsSuggestion(
            targetCalories = 500.0,
            targetProtein = 30.0
        )
        assertThat(result).containsExactly(mealWithFullNutrition)
    }

}

