package logic.use_case

import logic.MealsProvider
import model.Meal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class SearchFoodByDateUseCase(private val mealsProvider: MealsProvider) {

    class InvalidDateFormatException(message: String) : Exception(message)
    class NoMealsFoundException(message: String) : Exception(message)

    private val dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy")

    fun execute(dateInput: String): List<Meal> {
        val searchDate = try {
            LocalDate.parse(dateInput, dateFormatter)
        } catch (e: DateTimeParseException) {
            throw InvalidDateFormatException("Invalid date format. Please use M/d/yyyy (e.g., 4/11/2024).")
        }

        val meals = mealsProvider.getMeals().filter { it.addDate == searchDate }

        if (meals.isEmpty()) {
            throw NoMealsFoundException("No meals found for date: $dateInput")
        }

        return meals
    }
}
