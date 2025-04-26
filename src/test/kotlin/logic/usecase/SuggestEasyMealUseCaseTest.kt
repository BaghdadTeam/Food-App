package logic.usecase

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestEasyMealUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var suggestEasyMealUseCase: SuggestEasyMealUseCase

    @BeforeEach
    fun setup() {
        mealsProvider = mockk()
        suggestEasyMealUseCase = SuggestEasyMealUseCase(mealsProvider)
    }

    @Test
    fun `should throw NoMealFoundException when no meals available`() {
        every { mealsProvider.getMeals() } returns emptyList()
        assertThrows<NoMealFoundException> { suggestEasyMealUseCase.execute() }
    }


    @Test
    fun `should throw NoMealFoundException when no easy meals available`() {
        val complexMeal = createMealHelper(
            preparationTime = 60,
            ingredients = manyIngredients(),
            steps = manySteps()
        )
        every { mealsProvider.getMeals() } returns listOf(complexMeal)
        assertThrows<NoMealFoundException> { suggestEasyMealUseCase.execute() }
    }

    @Test
    fun `should return easy meals matching all criteria`() {
        val easyMeal = createMealHelper(
            preparationTime = 20,
            ingredients = fewIngredients(),
            steps = fewSteps()
        )
        every { mealsProvider.getMeals() } returns listOf(easyMeal)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(easyMeal)
    }

    @Test
    fun `should filter out meals with too many ingredients`() {
        val easyMeal = createMealHelper(
            preparationTime = 20,
            ingredients = fewIngredients(),
            steps = fewSteps()
        )
        val tooManyIngredientsMeal = createMealHelper(
            preparationTime = 20,
            ingredients = manyIngredients(),
            steps = fewSteps()
        )
        every { mealsProvider.getMeals() } returns listOf(easyMeal, tooManyIngredientsMeal)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(easyMeal)
    }

    @Test
    fun `should filter out meals with too many steps`() {
        val easyMeal = createMealHelper(
            preparationTime = 20,
            ingredients = fewIngredients(),
            steps = fewSteps()
        )
        val tooManyStepsMeal = createMealHelper(
            preparationTime = 20,
            ingredients = fewIngredients(),
            steps = manySteps()
        )
        every { mealsProvider.getMeals() } returns listOf(easyMeal, tooManyStepsMeal)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(easyMeal)
    }

    @Test
    fun `should filter out meals with too long preparation time`() {
        val easyMeal = createMealHelper(
            preparationTime = 20,
            ingredients = fewIngredients(),
            steps = fewSteps()
        )
        val longPrepTimeMeal = createMealHelper(
            preparationTime = 45,
            ingredients = fewIngredients(),
            steps = fewSteps()
        )
        every { mealsProvider.getMeals() } returns listOf(easyMeal, longPrepTimeMeal)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(easyMeal)
    }

    @Test
    fun `should return limited number of meals based on maxMealsToSuggest`() {
        val easyMeals = List(20) { index ->
            createMealHelper(
                name = "Easy Meal $index",
                preparationTime = 20,
                ingredients = listOf("item1", "item2"),
                steps = listOf("step1", "step2")
            )
        }
        every { mealsProvider.getMeals() } returns easyMeals
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).hasSize(10)
    }

    @Test
    fun `should handle empty ingredients list`() {
        val mealWithEmptyIngredients = createMealHelper(
            preparationTime = 20,
            ingredients = emptyList(),
            steps = fewSteps()
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithEmptyIngredients)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(mealWithEmptyIngredients)
    }

    @Test
    fun `should handle null ingredients`() {
        val mealWithNullIngredients = createMealHelper(
            preparationTime = 20,
            ingredients = null,
            steps = fewSteps()
        )
        every { mealsProvider.getMeals() } returns listOf(mealWithNullIngredients)
        val result = suggestEasyMealUseCase.execute()
        assertThat(result).containsExactly(mealWithNullIngredients)
    }

    @Test
    fun `should throw IllegalArgumentException when maxMealsToSuggest is negative`() {
        val mealsProvider = mockk<MealsProvider>()
        val useCase = SuggestEasyMealUseCase(mealsProvider, maxMealsToSuggest = -1)
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(
                preparationTime = 10,
                ingredients = fewIngredients(),
                steps = fewSteps()
            )
        )

        assertThrows<IllegalArgumentException> { useCase.execute() }
    }


    private fun manyIngredients() = listOf("item1", "item2", "item3", "item4", "item5", "item6")
    private fun manySteps() = listOf("step1", "step2", "step3", "step4", "step5", "step6", "step7")
    private fun fewIngredients() = listOf("item1", "item2", "item3")
    private fun fewSteps() = listOf("step1", "step2", "step3")
}
