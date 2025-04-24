package data

import com.google.common.truth.Truth.assertThat
import helpers.suggest.EggFreeSweetsTestData
import io.mockk.every
import io.mockk.mockk
import org.example.data.CsvMealsRepository
import org.example.data.DefaultMealsProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class DefaultMealsProviderTest {

    private lateinit var repository: CsvMealsRepository

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun `should return a list of meals from the repository`() {
        // Given
        every { repository.getAllMeals() } returns EggFreeSweetsTestData.allTestMeals()

        // When
        val result = DefaultMealsProvider(repository).getMeals()

        // Then
        assertThat(result).containsExactlyElementsIn(EggFreeSweetsTestData.allTestMeals())
    }

    @Test
    fun `should return empty list repository throws an error`() {
        // Given
        every { repository.getAllMeals() } throws RuntimeException()

        // When
        val result = DefaultMealsProvider(repository).getMeals()

        // Then
        assertTrue(result.isEmpty())
    }
}