package logic.usecase.search

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import logic.usecase.SearchMealUseCase
import org.example.logic.search.KMPSearchAlgorithm
import org.example.utils.EmptyMealNameException
import org.example.utils.EmptyMealsException
import org.example.utils.EmptyTextException

import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class SearchMealUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var searchMeal: SearchMealUseCase
    private lateinit var kMPSearchAlgorithm: KMPSearchAlgorithm

    @BeforeEach
    fun setup() {
        kMPSearchAlgorithm = KMPSearchAlgorithm()
        mealsProvider = mockk(relaxed = true)
        searchMeal = SearchMealUseCase(mealsProvider, kMPSearchAlgorithm)
    }


    @Test
    fun `should throw EmptyMealsException when MealsProvider returns empty list`() {
        // Given
        every { mealsProvider.getMeals() } returns emptyList()
        // When & Then
        assertThrows<EmptyMealsException> { searchMeal.execute("aboud") }
    }


    @Test
    fun `should throw EmptyMealNameException when empty meal name input`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "pizza")
        )
        // When & Then
        assertThrows<EmptyMealNameException> { searchMeal.execute("") }
    }

    @Test
    fun `should throw EmptyMealNameException when blank meal name input`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "pizza")
        )
        // When & Then
        assertThrows<EmptyMealNameException> { searchMeal.execute(" ") }
    }


    @Test
    fun `Should return list of meals when the Meal is found by name`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "pizza")
        )
        // When
        val meal = searchMeal.execute("pizza")

        // Then
        assertThat(meal[0].name).isEqualTo("pizza")

    }


    @Test
    fun `should throw an NoMealFoundException when no meal found by name`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "meal")
        )
        // When & Then
        assertThrows<NoMealFoundException> { searchMeal.execute("pizza") }
    }

    @Test
    fun `should throw an EmptyTextException when meal is null in database`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = null)
        )
        // When & Then
        assertThrows<EmptyTextException> { searchMeal.execute("pizza") }
    }
    @Test
    fun `should throw an EmptyTextException when meal is empty in database`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "")
        )
        // When & Then
        assertThrows<EmptyTextException> { searchMeal.execute("pizza") }
    }
    @Test
    fun `should throw an EmptyTextException when meal is blank in database`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = " ")
        )
        // When & Then
        assertThrows<EmptyTextException> { searchMeal.execute("pizza") }
    }

    @Test
    fun `should return list of meals when case insensitive search`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "Pizza")
        )
        // When
        val meal = searchMeal.execute("pizza")

        // Then
        assertThat(meal[0].name).isEqualTo("Pizza")

    }

    @Test
    fun `should return multiple meals when multiple meals found`() {
        // Given
        every { mealsProvider.getMeals() } returns listOf(
            createMealHelper(name = "Pizza"),
            createMealHelper(name = "Aboud Pizza")

        )
        // When
        val meal = searchMeal.execute("pizza")

        // Then
        assertThat(meal.size).isGreaterThan(1)
    }


}