package org.example

import model.Meal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

//class FoodByDate {
//    private val meals = mutableListOf<Meal>()
//    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    fun findMealsByDate(dateString: String): List<Meal> {
//        return try {
//            val searchDate = LocalDate.parse(dateString, dateFormatter)
//            meals.filter { it.addDate == searchDate }
//        } catch (e: DateTimeParseException) {
//            throw IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD")
//        }
//    }
//
//    fun findMealById(id: Int): Meal {
//        return meals.find { it.id == id }
//            ?: throw NoSuchElementException("Meal with ID $id not found")
//    }
//}