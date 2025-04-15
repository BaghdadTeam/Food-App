package data

import java.io.File

/**
 * A class to read and parse CSV files.
 *
 * @property file The CSV file to be read.
 */
class CsvReader(private val file: File) {

    /**
     * Reads the CSV file and returns a list of records as strings.
     * The first line (header) is skipped.
     *
     * @return A list of strings, each representing a record in the CSV file.
     */
    fun readCsv(): List<String> {
        val content = file.readText()
        return recordsSplitter(content).drop(1)
    }

    /**
     * Splits the content of the CSV file into individual records.
     * Handles quoted fields and ignores carriage return characters.
     *
     * @param content The content of the CSV file as a string.
     * @return A list of strings, each representing a record.
     */
    private fun recordsSplitter(content: String): List<String> {
        val records = mutableListOf<String>()
        var currentRecord = StringBuilder()
        var inQuotes = false

        for (char in content) {
            when {
                char == '"' -> {
                    inQuotes = !inQuotes
                    currentRecord.append(char)
                }

                char == '\n' && !inQuotes -> {
                    records.add(currentRecord.toString())
                    currentRecord = StringBuilder()
                }

                char == '\r' -> {} // Ignore CR
                else -> currentRecord.append(char)
            }
        }
        if (currentRecord.isNotEmpty()) records.add(currentRecord.toString())
        return records
    }
}