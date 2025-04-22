package logic.usecase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import model.Meal
import model.Nutrition
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FilterQuickHealthyMealsUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: FilterQuickHealthyMealsUseCase

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk(relaxed = true)
        useCase = FilterQuickHealthyMealsUseCase(mealsProvider)
    }


    @Test
    fun `Should throw EmptyMealsException when there is no meals to filter`() {
        // Given
        every { mealsProvider.getMeals() } returns emptyList()
        // When & Then
        assertThrows<EmptyMealsException> { useCase.execute(count = 1) }
    }


    @Test
    fun `Should throw NoMealsFoundException when there is no meals found`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            Meal(
                name = "healthy meal",
                id = null,
                contributorId = null,
                date = null,
                tags = null,
                nutrition = null,
                nSteps = null,
                steps = listOf(),
                description = null,
                ingredients = null,
                nIngredients = null,
                preparationTime = 10
            )
        )
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute(count = 1) }
    }

    @Test
    fun `Should return only the valid meals even if count input bigger then the meals match the conditions `() {
        every { mealsProvider.getMeals() } returns listOf(
            Meal(
                name = "healthy meal",
                id = null,
                contributorId = null,
                date = null,
                tags = null,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
                nSteps = null,
                steps = listOf(),
                description = null,
                ingredients = null,
                nIngredients = null,
                preparationTime = 10
            )
        )
        val result = useCase.execute(count = 3)
        assertThat(result).hasSize(1)
    }

    @Test
    fun `Should return meals with lowest total fat , saturated fat and carbohydrates compared to other meals`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            Meal(
                name = "healthy meal",
                id = 1,
                contributorId = null,
                date = null,
                tags = null,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
                nSteps = null,
                steps = listOf(),
                description = null,
                ingredients = null,
                nIngredients = null,
                preparationTime = 10
            ), Meal(
                name = "healthy meal",
                id = 2,
                contributorId = null,
                date = null,
                tags = null,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 20.0,
                    saturatedFat = 10.0,
                    carbohydrates = 5.0,
                    sugar = null,
                    sodium = null
                ),
                nSteps = null,
                steps = listOf(),
                description = null,
                ingredients = null,
                nIngredients = null,
                preparationTime = 10
            )
        )
        // When
        val result = useCase.execute(count = 1)
        // Then
        assertThat(result.first().id).isEqualTo(1)
    }

    @Test
    fun `Should throw NoMealsFoundException if preparation time longer than 15 minutes`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            Meal(
                name = "healthy meal",
                id = 1,
                contributorId = null,
                date = null,
                tags = null,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
                nSteps = null,
                steps = listOf(),
                description = null,
                ingredients = null,
                nIngredients = null,
                preparationTime = 20
            )
        )
        // When & then
        assertThrows<NoMealFoundException> { useCase.execute(count = 1) }
    }

}