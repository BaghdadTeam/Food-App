package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import logic.helpers.createMealHelper
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
    fun `should return sweet meals that does not contain eggs in the ingredients`() {
        val eggFreeSweet = createMealHelper(
            name = "Grilled Venison Burgers",
            tags = listOf("sweet"),
            ingredients = listOf("meat", "salt", "pepper")
        )
        val sweetWithEgg = createMealHelper(
            name = "Deep Fried Dessert Thingys",
            tags = listOf("sweet"),
            ingredients = listOf("egg", "flour", "sugar")
        )
        val nonSweetMeal = createMealHelper(
            name = "Savory Minestrone",
            tags = listOf("savory"),
            ingredients = listOf("beans", "garlic", "tomatoes")
        )
        // Given
        every { mealsProvider.getMeals() } returns listOf(eggFreeSweet, sweetWithEgg, nonSweetMeal)

        // When
        val result = eggFreeSweetsUseCase.execute()

        // Then
        assertThat(result).isEqualTo(eggFreeSweet)
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
        val sweetWithEgg = createMealHelper(
            name = "Deep Fried Dessert Thingys",
            tags = listOf("sweet"),
            ingredients = listOf("egg", "flour", "sugar")
        )
        val nonSweetMeal = createMealHelper(
            name = "Savory Minestrone",
            tags = listOf("savory"),
            ingredients = listOf("beans", "garlic", "tomatoes")
        )
        // Given
        every { mealsProvider.getMeals() } returns listOf(sweetWithEgg, nonSweetMeal)

        // When & Then

        assertThrows<NoMealFoundException> {
            eggFreeSweetsUseCase.execute()
        }
    }

    @Test
    fun `should throw NoMealFoundException if the same meal is already seen`() {
        val eggFreeSweet = createMealHelper(
            name = "Grilled Venison Burgers",
            tags = listOf("sweet"),
            ingredients = listOf("meat", "salt", "pepper")
        )
        // Given
        every { mealsProvider.getMeals() } returns listOf(eggFreeSweet)

        // First execution to see the meal
        val result = eggFreeSweetsUseCase.execute()
        assertThat(result).isEqualTo(eggFreeSweet)

        // Second execution throws exception
        assertThrows<NoMealFoundException> {
            eggFreeSweetsUseCase.execute()
        }
    }
}
