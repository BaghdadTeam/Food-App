package logic.usecase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import helpers.createMealHelper
import model.Nutrition
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FilterQuickHealthyMealsUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: FilterQuickHealthyMealsUseCase

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk()
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
    fun `Should throw NoMealsFoundException when the nutrition is null`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(
                nutrition = null,
                preparationTime = 10
            )
        )
        // When & Then
        assertThrows<NoMealFoundException> { useCase.execute(count = 1) }
    }


    @Test
    fun `Should return only the valid meals even if count input bigger then the meals match the conditions `() {
        every { mealsProvider.getMeals() } returns listOf(
           createMealHelper(
                name = "healthy meal",
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
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
           createMealHelper(
                id = 1,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),

                preparationTime = 10
            ), createMealHelper(
                id = 2,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 20.0,
                    saturatedFat = 10.0,
                    carbohydrates = 5.0,
                    sugar = null,
                    sodium = null
                ),
                preparationTime = 10
            ), createMealHelper(
                id = 3,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 2.0,
                    saturatedFat = 3.0,
                    carbohydrates = 5.0,
                    sugar = null,
                    sodium = null
                ),
                preparationTime = 10
            )
        )
        // When
        val result = useCase.execute(count = 1)
        // Then
        assertThat(result.first().id).isEqualTo(3)
    }

    @ParameterizedTest
    @CsvSource("-10", "20")
    fun `Should throw NoMealsFoundException if preparation time not in range between 0 and 15  minutes `(preparationTime: Int) {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
           createMealHelper(
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
             preparationTime = preparationTime
            )
        )
        // When & then
        assertThrows<NoMealFoundException> { useCase.execute(count = 1) }
    }

    @ParameterizedTest
    @CsvSource("5", "15", "0")
    fun `Should return meals with  preparation time in range between 0 and 15  minutes `(preparationTime: Int) {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = 10.0,
                    saturatedFat = 5.0,
                    carbohydrates = 2.0,
                    sugar = null,
                    sodium = null
                ),
                preparationTime = preparationTime
            )
        )

        //When
        val result = useCase.execute(count = 1)
        // Then
        assertThat(result.first().preparationTime).isIn(0..15)
    }
    @Test
    fun `Should treat null nutrition values as zero when calculating health score`() {
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(
                name = "healthy meal",
                nutrition = Nutrition(null,null,null,null,null,null,null)
            ,
                preparationTime = 10
            )
        )
        val result = useCase.execute(count = 1)
        assertThat(result).isNotEmpty()
    }

}