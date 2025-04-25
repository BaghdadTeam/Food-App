package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import helpers.game.IngredientGameTestData.fixedOptions
import helpers.game.IngredientGameTestData.inValidMeals
import helpers.game.IngredientGameTestData.meals
import org.example.logic.usecase.game.IngredientGameUseCase
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IngredientGameUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var ingredientGameUseCase: IngredientGameUseCase

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk(relaxed = true)
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
    }

    @Test
    fun `should return true when answer is correct `() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `should increase score when answer is correct`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should increase score over multiple correct answers`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 1)
        ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(2000)
    }

    @Test
    fun `execute should return true when correct option is chosen`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 1)
        assertThat(result).isTrue()
    }


    @Test
    fun `should return false when invalid choice`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 3)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `should return false for out of bounds index`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 10)
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when choice is null`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, null)

        assertThat(result).isFalse()
    }

    @Test
    fun `should not increase score for invalid choice`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 3)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }


    @Test
    fun `isGameOver should return true when reached 15 try`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // Ensure the test starts fresh

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            val choice = options!!.options.indexOf(options.correctIngredient) + 1
            ingredientGameUseCase.execute(options, choice)
        }

        val isOver = ingredientGameUseCase.isGameOver()
        assertTrue(isOver)
    }

    @Test
    fun `isGameOver should return score of 15000 after 15 correct answers`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // Ensure the test starts fresh

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            val choice = options!!.options.indexOf(options.correctIngredient) + 1
            ingredientGameUseCase.execute(options, choice)
        }

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(15000)
    }

    @Test
    fun `isGameOver should return false when correctAnswers are less than 15`() {
        repeat(10) {
            val q = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                fixedOptions,
                q?.options?.indexOf(q.correctIngredient)?.plus(1)
            )
        }
        assertThat(ingredientGameUseCase.isGameOver()).isEqualTo(false)
    }


    @Test
    fun `getOptions should return null when no unused meals are available`() {
        every { mealsProvider.getMeals() } returns meals

        repeat(1) { ingredientGameUseCase.getOptions() }

        val result = ingredientGameUseCase.getOptions()

        assertEquals(null, result)
    }

    @Test
    fun `getOptions should return 3 unique options`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // why?

        val options = ingredientGameUseCase.getOptions()

        assertEquals(3, options?.options?.distinct()?.size)
    }

    @Test
    fun `getOptions should return null if no meals available`() {
        every { mealsProvider.getMeals() } returns emptyList()

        val options = ingredientGameUseCase.getOptions()

        assertThat(options).isNull()
    }

    @Test
    fun `getOptions should use only valid meals that are not null`() {

        every { mealsProvider.getMeals() } returns inValidMeals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        val options = ingredientGameUseCase.getOptions()

        val result = ingredientGameUseCase.getMealName(options)
        assertEquals("Veggie Stir Fry", result)
    }


    @Test
    fun `getQuestionNumber should increase with correct answers`() {
        every { mealsProvider.getMeals() } returns meals

        assertEquals(1, ingredientGameUseCase.getQuestionNumber())
        ingredientGameUseCase.execute(fixedOptions, 1)
        assertEquals(2, ingredientGameUseCase.getQuestionNumber())
    }

    @Test
    fun `getNextMeal should return the next unused meal`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

        val result = ingredientGameUseCase.getMealName(ingredientGameUseCase.getOptions())

        assertThat(result).isEqualTo("Breakfast Pizza")
    }

    @Test
    fun `getMealName should return the correct meal name`() {
        val result = ingredientGameUseCase.getMealName(fixedOptions)
        assertThat(result).isEqualTo("Pizza")
    }

    @Test
    fun `getMealName should return null string when options is null`() {
        val result = ingredientGameUseCase.getMealName(null)
        assertEquals("null", result)
    }

    @Test
    fun `getPoints should return 1000`() {
        val result = ingredientGameUseCase.getPoints()
        assertThat(result).isEqualTo(1000)
    }

    @Test
    fun `getOptions should not fail when some meals have null ingredients`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        // When
        val question = ingredientGameUseCase.getOptions()

        // Then: Assert the question is generated and contains correct data
        assertThat(question).isNotNull()
        assertThat(question?.options?.size).isEqualTo(3)
    }

    @Test
    fun `getOptions should ignore meals with null or empty ingredients`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        val question = ingredientGameUseCase.getOptions()

        assertThat(question).isNotNull()
        assertThat(question?.options).hasSize(3)
        assertThat(question?.options).doesNotContain(null)
        assertThat(question?.correctIngredient).isIn(question?.options)
    }

    @Test
    fun `only valid meals are filtered and used to generate questions`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

        val generatedMeals = mutableSetOf<String>()
        repeat(3) {
            val question = ingredientGameUseCase.getOptions()
            question?.let { generatedMeals.add(it.mealName) }
        }

        val expectedValidMeals = listOf("Breakfast Pizza", "Chicken Alfredo", "Beef Tacos")

        assertThat(generatedMeals).containsExactlyElementsIn(expectedValidMeals)
    }
}