package org.example.data

import kotlinx.datetime.LocalDate
import model.Meal
import model.Nutrition
import org.example.utils.EmptyRecordException

/**
 * A parser for processing records and converting them into `Meal` objects.
 */
class RecordParser {

    /**
     * Parses a single record string into a `Meal` object.
     *
     * @param record The raw record string to parse.
     * @return A `Meal` object containing the parsed data.
     */
    fun parseRecord(record: String): Meal {

        if (record.isEmpty()) throw EmptyRecordException("The record is empty can't be parsed")

        val row = Regex(""",(?=(?:[^"]*"[^"]*")*[^"]*$)""")
            .split(record)
            .map { it.trim().removeSurrounding("\"") }

        return Meal(
            name = row[ColumnIndex.NAME],
            id = row[ColumnIndex.ID].toInt(),
            preparationTime = row[ColumnIndex.MINUTES].toInt(),
            contributorId = row[ColumnIndex.CONTRIBUTOR_ID].toInt(),
            date = LocalDate.parse(row[ColumnIndex.SUBMITTED]),
            tags = parseStringList(row[ColumnIndex.TAGS]),
            nutrition = parseNutrition(row[ColumnIndex.NUTRITION]),
            nSteps = row[ColumnIndex.NUMBER_OF_STEPS].toInt(),
            steps = parseStringList(row[ColumnIndex.STEPS]),
            description = row[ColumnIndex.DESCRIPTION],
            ingredients = parseStringList(row[ColumnIndex.DEFAULT_INGREDIENTS]),
            nIngredients = row[ColumnIndex.DEFAULT_NUMBER_OF_INGREDIENTS].toInt(),

            )
    }

    /**
     * Parses a raw string into a list of strings.
     *
     * @param raw The raw string to parse, typically formatted as a list-like string.
     * @return A list of strings extracted from the raw input.
     */
    private fun parseStringList(raw: String): List<String> {
        return if (raw.isEmpty()) {
            emptyList()
        }
        else {
            raw.removePrefix("['")
                .removeSuffix("']")
                .split("', '")
                .map { it.trim().removeSurrounding("'") }
                .filter { it.isNotBlank() }
        }
    }

    /**
     * Parses a raw string into a `Nutrition` object.
     *
     * @param raw The raw string to parse, typically formatted as a list of nutritional values.
     * @return A `Nutrition` object containing the parsed nutritional data.
     */
    private fun parseNutrition(raw: String): Nutrition {
        if (raw.isEmpty()) return Nutrition(
            calories = 0.0,
            totalFat = 0.0,
            sugar = 0.0,
            sodium = 0.0,
            protein = 0.0,
            saturatedFat = 0.0,
            carbohydrates = 0.0
        )
        val cleaned = raw.removePrefix("[").removeSuffix("]").trim()
        val values = cleaned.split(",")
            .map { it.trim().toDouble() }
        return Nutrition(
            calories = values[ColumnIndex.CALORIES],
            totalFat = values[ColumnIndex.TOTAL_FAT],
            sugar = values[ColumnIndex.SUGAR],
            sodium = values[ColumnIndex.SODIUM],
            protein = values[ColumnIndex.PROTEIN],
            saturatedFat = values[ColumnIndex.SATURATED_FAT],
            carbohydrates = values[ColumnIndex.CARBOHYDRATES]
        )
    }
}