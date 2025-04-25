package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import model.Meal
import org.example.logic.usecase.game.GuessMealGameUseCase
import org.example.model.GuessFeedback
import org.example.utils.EmptyMealsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test


class GuessMealGameUseCaseTest {

    private lateinit var mealsProvider: MealsProvider

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk()
    }


    @Test
    fun `should throw EmptyMealsException when no meals available`() {
        every { mealsProvider.getMeals() } returns emptyList()

        assertThrows<EmptyMealsException> {
            GuessMealGameUseCase(mealsProvider)
        }
    }

    @Test
    fun `should throw EmptyMealsException when filtered list is empty`() {
        val mealsWithNulls = listOf(
            createMealHelper(name = null, preparationTime = 10),
            createMealHelper(name = "Burger", preparationTime = null)
        )
        every { mealsProvider.getMeals() } returns mealsWithNulls

        assertThrows<EmptyMealsException> {
            GuessMealGameUseCase(mealsProvider)
        }
    }

    @Test
    fun `should initialize with valid meal from mixed list`() {
        val mixedMeals = listOf(
            createMealHelper(name = null, preparationTime = 10),
            createMealHelper(name = "Valid Meal", preparationTime = 20),
            createMealHelper(name = "Another Meal", preparationTime = null)
        )
        every { mealsProvider.getMeals() } returns mixedMeals

        val useCase = GuessMealGameUseCase(mealsProvider)

        assertThat(useCase.getMealName()).isEqualTo("Valid Meal")
    }

    @Test
    fun `should always select valid meal from filtered list`() {
        val validMeals = listOf(
            createMealHelper(name = "Meal 1", preparationTime = 10),
            createMealHelper(name = "Meal 2", preparationTime = 20)
        )
        every { mealsProvider.getMeals() } returns validMeals

        repeat(50) { // Test multiple random selections
            val useCase = GuessMealGameUseCase(mealsProvider)
            assertThat(useCase.getMealName()).isNotNull()
            assertThat(useCase.getCorrectTime()).isAtLeast(0)
        }
    }

    @Test
    fun `should initialize with valid meal when mixed with invalid meals`() {
        val meals = listOf(
            createMealHelper(name = null, preparationTime = 10),
            createMealHelper(name = "Valid Meal", preparationTime = 30)
        )
        every { mealsProvider.getMeals() } returns meals

        val useCase = GuessMealGameUseCase(mealsProvider)

        assertThat(useCase.getMealName()).isEqualTo("Valid Meal")
    }

    @Test
    fun `correct guess should end game immediately`() {
        val meal = createMealHelper(name = "Pizza", preparationTime = 30)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        val result = useCase.execute(30)

        assertThat(result).isEqualTo(GuessFeedback.Correct)
        assertThat(useCase.isGameOver()).isTrue()
        assertThat(useCase.getAttemptsLeft()).isEqualTo(3)
    }

    @Test
    fun `null guess should return Invalid without changing attempts`() {
        val meal = createMealHelper(name = "Burger", preparationTime = 20)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        val result = useCase.execute(null)

        assertThat(result).isEqualTo(GuessFeedback.Invalid)
        assertThat(useCase.getAttemptsLeft()).isEqualTo(3)
    }

    @Test
    fun `too low guess should decrease attempts and return TooLow`() {
        val meal = createMealHelper(name = "Salad", preparationTime = 15)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        val result = useCase.execute(10)

        assertThat(result).isEqualTo(GuessFeedback.TooLow)
        assertThat(useCase.getAttemptsLeft()).isEqualTo(2)
        assertThat(useCase.isGameOver()).isFalse()
    }

    @Test
    fun `too high guess should decrease attempts and return TooHigh`() {
        val meal = createMealHelper(name = "Pasta", preparationTime = 25)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        val result = useCase.execute(30)

        assertThat(result).isEqualTo(GuessFeedback.TooHigh)
        assertThat(useCase.getAttemptsLeft()).isEqualTo(2)
        assertThat(useCase.isGameOver()).isFalse()
    }

    @Test
    fun `game should end after 3 incorrect guesses`() {
        val meal = createMealHelper(name = "Steak", preparationTime = 40)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        useCase.execute(35) // 1st wrong (TooLow)
        useCase.execute(30) // 2nd wrong (TooLow)
        useCase.execute(25) // 3rd wrong (TooHigh)

        assertThat(useCase.isGameOver()).isTrue()
        assertThat(useCase.getAttemptsLeft()).isEqualTo(0)
    }

    @Test
    fun `game should not end with remaining attempts`() {
        val meal = createMealHelper(name = "Soup", preparationTime = 10)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        useCase.execute(5) // 1st wrong (TooLow)
        useCase.execute(15) // 2nd wrong (TooHigh)

        assertThat(useCase.isGameOver()).isFalse()
        assertThat(useCase.getAttemptsLeft()).isEqualTo(1)
    }

    @Test
    fun `should handle zero preparation time correctly`() {
        val meal = createMealHelper(name = "Instant Meal", preparationTime = 0)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        val result = useCase.execute(0)

        assertThat(result).isEqualTo(GuessFeedback.Correct)
    }

    @Test
    fun `should allow access to correct time after game over`() {
        val meal = createMealHelper(name = "Revealed Meal", preparationTime = 8)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        useCase.execute(8) // Correct guess

        assertThat(useCase.getCorrectTime()).isEqualTo(8)
    }

    @Test
    fun `should persist correct time through multiple guesses`() {
        val meal = createMealHelper(name = "Consistent Meal", preparationTime = 12)
        every { mealsProvider.getMeals() } returns listOf(meal)
        val useCase = GuessMealGameUseCase(mealsProvider)

        useCase.execute(10)
        useCase.execute(15)
        useCase.getCorrectTime() // Should throw before game over

        useCase.execute(12) // Correct guess

        assertThat(useCase.getCorrectTime()).isEqualTo(12)
    }
}