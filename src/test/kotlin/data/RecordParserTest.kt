package data

import com.google.common.truth.Truth.assertThat
import helpers.data.RecordParserTestData
import org.example.data.RecordParser
import org.example.utils.EmptyRecordException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RecordParserTest {

    private val parser: RecordParser = RecordParser()

    @Test
    fun `should return correct meal object from a valid input`() {
        // Given
        val record = RecordParserTestData.recordWithAllDataCorrect()
        val expectedMeal = RecordParserTestData.mealWithAllDataCorrect()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with empty string for description`() {
        // Given
        val record = RecordParserTestData.recordWithNoDescription()
        val expectedMeal = RecordParserTestData.mealWithNoDescription()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with empty tags if there is non`() {
        // Given
        val record = RecordParserTestData.recordWithMissingTags()
        val expectedMeal = RecordParserTestData.mealWithMissingTags()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with empty nutrition if there is non`() {
        // Given
        val record = RecordParserTestData.recordWithMissingNutrition()
        val expectedMeal = RecordParserTestData.mealWithMissingNutrition()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with empty steps if there is non`() {
        // Given
        val record = RecordParserTestData.recordWithMissingSteps()
        val expectedMeal = RecordParserTestData.mealWithMissingSteps()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should returns meal without any empty items in steps`() {
        // Given
        val record = RecordParserTestData.recordWithMissingStep()
        val expectedMeal = RecordParserTestData.mealWithMissingStep()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with multi-line description`() {
        // Given
        val record = RecordParserTestData.recordWithMultiLineDescription()
        val expectedMeal = RecordParserTestData.mealWithMultiLineDescription()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should return meal with empty ingredients if there is non`() {
        // Given
        val record = RecordParserTestData.recordWithMissingIngredients()
        val expectedMeal = RecordParserTestData.mealWithMissingIngredients()

        // When
        val result = parser.parseRecord(record)

        // Then
        assertThat(result).isEqualTo(expectedMeal)
    }

    @Test
    fun `should throw EmptyRecordException when the record is empty`() {
        // Given
        val record = RecordParserTestData.emptyRecord()

        // When & Then
        assertThrows<EmptyRecordException> {
            parser.parseRecord(record)
        }
    }
}