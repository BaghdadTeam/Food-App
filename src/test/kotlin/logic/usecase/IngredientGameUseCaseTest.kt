package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import logic.MealsProvider
import model.Meal
import model.Nutrition
import org.example.model.IngredientQuestion
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IngredientGameUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var ingredientGameUseCase: IngredientGameUseCase


    private var meal: Meal = Meal(
        name = "Breakfast Pizza",
        id = 31490,
        contributorId = 26278,
        date = LocalDate.parse("2002-06-17"),
        tags = listOf("30-minutes-or-less", "time-to-make", "course"),
        nutrition = Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0),
        nSteps = 9,
        steps = listOf("Preheat oven", "Press dough", "Bake 5 minutes"),
        description = "A quick breakfast pizza recipe",
        ingredients = listOf("Cheese", "Tomato", "Lettuce", "Olive", "Onion", "Beef"),
        nIngredients = 3,
        preparationTime = 30
    )

//    fun fakeQuestion(correct: String = "Cheese") = IngredientQuestion(
//        "Pizza", correct, listOf("Cheese", "Tomato", "Dough")
//    )

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk(relaxed = true)
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

    }


    @Test
    fun `should return true when answer is correct and update score`() {

        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        val result = ingredientGameUseCase.execute(options, 1)

        assertThat(result).isEqualTo(true)
//        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should update score when answer is correct`() {

        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

         ingredientGameUseCase.execute(options, 1)

//        assertThat(result).isEqualTo(true)
        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should handle invalid choice correctly`() {
        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        val result = ingredientGameUseCase.execute(options, 3)

        assertThat(result).isEqualTo(false)
//        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }

    @Test
    fun `should not increase score for invalid choice`() {
        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        ingredientGameUseCase.execute(options, 3) // Invalid choice

//        assertThat(result).isEqualTo(false)
        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }

    //    @Test
//    fun `should handle game over scenario`() {
//        val options = IngredientQuestion(
//            mealName = "Pizza",
//            correctIngredient = "Cheese",
//            options = listOf("Cheese", "lattice", "sugar")
//        )
//
//        // Mock no meals available, meaning the game is over
//        every { mealsProvider.getMeals() } returns emptyList()
//
//        val result = ingredientGameUseCase.execute(options, 1)
//
//        assertFalse(result)
//    }

    @Test
    fun `should handle game over scenario when reached 15 try`() {
        //todo make fake meals
        val fakeMeals = List(20) { i -> meal.copy(id = i, name = "Meal $i", ingredients = listOf("Cheese", "Sugar", "Lettuce")) }
        every { mealsProvider.getMeals() } returns fakeMeals

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                options!!,
                options.options.indexOf(options.correctIngredient) + 1
            )
        }

        val isOver = ingredientGameUseCase.isGameOver()
//        println("Game Over! Final Score: ${ingredientGameUseCase.getScore()} points")
        assertTrue(isOver)
    }


    @Test
    fun `getOptions should return null when no unused meals are available`() {
        every { mealsProvider.getMeals() } returns listOf(meal)

        repeat(1) { ingredientGameUseCase.getOptions() }

        val result = ingredientGameUseCase.getOptions()

        assertEquals(null, result)
    }

    @Test
    fun `getQuestionNumber should increase with correct answers`() {
        val options = IngredientQuestion(
            "Pizza", "Cheese", options = listOf("Cheese", "lattice", "sugar")
        )
        every { mealsProvider.getMeals() } returns listOf(meal)

        assertEquals(1, ingredientGameUseCase.getQuestionNumber())
        ingredientGameUseCase.execute(options, 1)
        assertEquals(2, ingredientGameUseCase.getQuestionNumber())
    }

    @Test
    fun `getMealName should return the correct meal name`() {
        val options = IngredientQuestion(
            "Pizza", "Cheese", options = listOf("Cheese", "lattice", "sugar")
        )

        val result = ingredientGameUseCase.getMealName(options)

//        assertEquals("Pizza", result)
        assertThat(result).isEqualTo("Pizza")
    }

    //error todo

    @Test
    fun `isGameOver should return true after 15 correct answers`() {
        every { mealsProvider.getMeals() } returns List(20) { index ->
            meal.copy(id = index + 1, name = "Meal $index")
        }

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                options!!,
                options.options.indexOf(options.correctIngredient) + 1
            )
        }

        assertTrue(ingredientGameUseCase.isGameOver())
        assertEquals(15_000, ingredientGameUseCase.getScore())
    }

    //error todo
    @Test
    fun `getOptions should return 3 unique options`() {
        every { mealsProvider.getMeals() } returns listOf(meal)

        val options = ingredientGameUseCase.getOptions()

        assertEquals(3, options?.options?.size)
        assertEquals(options?.options?.distinct()?.size, options?.options?.size) // all unique
    }


}