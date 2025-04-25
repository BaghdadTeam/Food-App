package org.example.logic.search

import org.example.logic.SearchAlgorithm
import org.example.utils.EmptyPatternException
import org.example.utils.EmptyTextException

class KMPSearchAlgorithm : SearchAlgorithm {

    override fun search(text: String, pattern: String): Boolean {
        val lps = constructLPS(pattern)
        return kmpSearch(text, pattern, lps)
    }

    /**
     * Searches for a pattern in text using the Knuth-Morris-Pratt (KMP) algorithm.
     * Uses a precomputed LPS array to skip unnecessary comparisons for efficient searching.
     *
     * @param text The text to search within
     * @param pattern The pattern to find
     * @return true if the pattern is found, false otherwise
     */
    private fun kmpSearch(text: String, pattern: String, lps: IntArray): Boolean {
        if (text.isEmpty() || text.isBlank()) throw EmptyTextException()

        val textLength = text.length
        val patternLength = pattern.length

        var textIndex = 0
        var patternIndex = 0

        while (textIndex < textLength) {
            if (text[textIndex] == pattern[patternIndex]) {
                textIndex++
                patternIndex++
            }
            if (patternIndex == patternLength) {
                return true
            } else if (textIndex < textLength && text[textIndex] != pattern[patternIndex]) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1]
                } else {
                    textIndex++
                }
            }
        }
        return false
    }

    /**
     * Creates the  (LPS) array for KMP algorithm.
     * The LPS array stores the length of the longest proper prefix which is also a suffix.
     *
     * @param pattern The pattern to analyze
     * @return LPS array with pattern's prefix-suffix information
     */
    private fun constructLPS(pattern: String): IntArray {
        if (pattern.isEmpty() || pattern.isBlank()) throw EmptyPatternException()


        val patternLength = pattern.length
        val lps = IntArray(patternLength)

        var len = 0
        var index = 1

        while (index < patternLength) {
            if (pattern[index] == pattern[len]) {
                len++
                lps[index] = len
                index++
            } else {
                if (len != 0) {
                    len = lps[len - 1]
                } else {
                    lps[index] = 0
                    index++
                }
            }
        }
        return lps
    }
}