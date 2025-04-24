package org.example.data

import model.Meal
import org.example.logic.MealsRepository
import org.example.utils.CsvLoadingException

class CsvMealsRepository(
    private val csvReader: CsvReader,
    private val recordParser: RecordParser
) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        try {
            return csvReader.readCsv().map { record ->
                recordParser.parseRecord(record)
            }
        } catch (e: Exception) {
            throw CsvLoadingException("Failed to load the csv file", e)
        }
    }
}