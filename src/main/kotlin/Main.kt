package org.example

import data.CsvReader
import data.RecordParser
import model.Meal
import org.example.ILPotato.displayRandomPotatoMeals
import org.example.ILPotato.findPotatoMeals
import java.io.File

fun main() {
    val csvRecords = CsvReader(File("A:\\Android Projects\\The Chance\\food.csv")).readCsv()
    val parser = RecordParser()
    val meals = mutableListOf<Meal>()
    csvRecords.forEach { record ->
        meals.add(parser.parseRecord(record))
    }

    // println("${meals[5].name?.trim()}: ${meals[5].description}")
    val potatoMeal = findPotatoMeals(meals)
    displayRandomPotatoMeals(potatoMeal)
}