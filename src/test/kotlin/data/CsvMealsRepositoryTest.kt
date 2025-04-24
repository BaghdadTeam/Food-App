package data

import com.google.common.truth.Truth.assertThat
import helpers.createMealHelper
import io.mockk.every
import io.mockk.mockk
import org.example.data.CsvMealsRepository
import org.example.utils.CsvLoadingException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CsvMealsRepositoryTest {

    private lateinit var csvReader: CsvReader
    private lateinit var recordParser: RecordParser
    private lateinit var repository: CsvMealsRepository

    @BeforeEach
    fun setup() {
        csvReader = mockk()
        recordParser = mockk()
        repository = CsvMealsRepository(csvReader, recordParser)
    }

    @Test
    fun `should return list of meals when the csv is parsed successfully`() {
        // Given
        val records = listOf("First record", "Second record")
        val meals = listOf(createMealHelper("Potato chips"), createMealHelper("Pizza"))

        every { csvReader.readCsv() } returns records
        every { recordParser.parseRecord("First record") } returns meals[0]
        every { recordParser.parseRecord("Second record") } returns meals[1]

        // When
        val result = repository.getAllMeals()

        // Then
        assertThat(result).containsExactlyElementsIn(meals)
    }

    @Test
    fun `should throw CsvLoadingException when there's something happened in the parsing operation`() {
        // Given
        val records = listOf("First record", "Second record")

        every { csvReader.readCsv() } returns records
        every { recordParser.parseRecord("First record") } returns createMealHelper("Potato chips")
        every { recordParser.parseRecord("Second record") } throws Exception("Parsing failed")

        // When & Then
        assertThrows<CsvLoadingException> {
            repository.getAllMeals()
        }
    }

    @Test
    fun `should throw CsvLoadingException if csv reader fails`() {
        // Given
        every { csvReader.readCsv() } throws RuntimeException("File not found")

        // When & Then
        assertThrows<CsvLoadingException> {
            repository.getAllMeals()
        }
    }
}