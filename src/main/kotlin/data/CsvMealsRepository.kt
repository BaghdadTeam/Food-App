package org.example.data

import data.CsvReader
import data.RecordParser
import model.Meal
import org.example.logic.MealsRepository

class CsvMealsRepository(
    private val csvReader: CsvReader,
    private val recordParser: RecordParser
) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return csvReader.readCsv().map { record ->
            recordParser.parseRecord(record)
        }
    }
}