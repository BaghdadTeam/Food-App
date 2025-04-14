package org.example.data

import data.CsvReader
import data.RecordParser
import model.Meal
import org.example.logic.MealsRepository

class CsvMealsRepository(
    private val csvReader: CsvReader,
    private val recordParser: RecordParser
): MealsRepository {
    override fun getAllMeals(): List<Meal> {
        val meals = mutableListOf<Meal>()
        csvReader.readCsv().forEach { record ->
            meals.add(recordParser.parseRecord(record))
        }
        return meals
    }
}