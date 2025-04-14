package data

import model.Meal
import model.Nutrition

class RecordParser {

    fun parseRecord(record: String): Meal {

        val row = Regex(""",(?=(?:[^"]*"[^"]*")*[^"]*$)""")
            .split(record)
            .map { it.trim().removeSurrounding("\"") }

        return Meal(
            name = row[ColumnIndex.NAME],
            id = row[ColumnIndex.ID].toInt(),
            timeToCock = row[ColumnIndex.MINUTES].toInt(),
            contributorId = row[ColumnIndex.CONTRIBUTOR_ID].toInt(),
            date = row[ColumnIndex.SUBMITTED],
            tags = parseStringList(row[ColumnIndex.TAGS]),
            nutrition = parseNutrition(row[ColumnIndex.NUTRITION]),
            nSteps = row[ColumnIndex.NUMBER_OF_STEPS].toInt(),
            steps = parseStringList(row[ColumnIndex.STEPS]),
            description = row[ColumnIndex.DESCRIPTION],
            ingredients = parseStringList(row[ColumnIndex.DEFAULT_INGREDIENTS]),
            nIngredients = row[ColumnIndex.DEFAULT_NUMBER_OF_INGREDIENTS].toInt()
        )
    }

    private fun parseStringList(raw: String): List<String> {
        return raw.removePrefix("['")
            .removeSuffix("']")
            .split("', '")
            .map { it.trim().removeSurrounding("'") }
            .filter { it.isNotBlank() }
    }

    private fun parseNutrition(raw: String): Nutrition {
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