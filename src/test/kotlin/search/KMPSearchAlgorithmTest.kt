package search

import com.google.common.truth.Truth.assertThat
import org.example.logic.search.KMPSearchAlgorithm
import org.example.utils.EmptyPatternException
import org.example.utils.EmptyUserInputException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test


class KMPSearchAlgorithmTest {
    private lateinit var kmpSearchAlgorithm: KMPSearchAlgorithm

    @BeforeEach
    fun setup() {
        kmpSearchAlgorithm = KMPSearchAlgorithm()
    }

    @Test
    fun `should throw an exception when pattern is empty`() {
        // Given
        val text = "pizza"
        val pattern = ""

        // when & then
        assertThrows<EmptyPatternException> { kmpSearchAlgorithm.search(text, pattern) }

    }

    @Test
    fun `should throw and exception when text is empty`() {
        // Given
        val text = ""
        val pattern = "pizza"

        // when & then
        assertThrows<EmptyUserInputException> { kmpSearchAlgorithm.search(text, pattern) }
    }

    @Test
    fun `should return true when pattern present at the beginning of text`() {
        // Given
        val text = "pizza is delicious"
        val pattern = "pizza"

        // When
        val result = kmpSearchAlgorithm.search(text, pattern)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when pattern present in the middle of text`() {
        // Given
        val text = "is pizza delicious"
        val pattern = "pizza"

        // When
        val result = kmpSearchAlgorithm.search(text, pattern)

        // Then
        assertThat(result).isTrue()

    }

    @Test
    fun `should return true when pattern present at the end of text`() {
        // Given
        val text = "Try the unique flavors of iraqi pizza"
        val pattern = "iraqi pizza"

        // When
        val result = kmpSearchAlgorithm.search(text, pattern)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when pattern not present in text`() {
        // Given
        val text = "i am hungry"
        val pattern = "pizza"

        // When
        val result = kmpSearchAlgorithm.search(text, pattern)

        // Then
        assertThat(result).isFalse()
    }


    @Test
    fun `should return false when pattern is longer than text`() {
        // Given
        val text = "iraqi pizza"

        val pattern = "iraqi pizza Culinary Delight: The Grand Pizza Experience Extravaganza."

        // When
        val result = kmpSearchAlgorithm.search(text, pattern)

        // Then
        assertThat(result).isFalse()
    }

}