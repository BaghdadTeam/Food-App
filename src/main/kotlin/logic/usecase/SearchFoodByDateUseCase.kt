package logic.usecase

import kotlinx.datetime.LocalDate
import logic.MealsProvider
import model.Meal
import org.example.utils.InvalidDateFormatException
import org.example.utils.NoMealsFoundException
import java.time.format.DateTimeParseException

class SearchFoodByDateUseCase(private val mealsProvider: MealsProvider) {


    fun execute(dateInput: String): List<Meal> {
        val searchDate = try {
            LocalDate.parse(dateInput)
        } catch (e: DateTimeParseException) {
            throw InvalidDateFormatException("Invalid date format. Please use yyyy-m-d (e.g., 2024-01-04).")
        }

        val meals = mealsProvider.getMeals().filter { it.date == searchDate }

        if (meals.isEmpty()) {
            throw NoMealsFoundException("No meals found for date: $dateInput")
        }

        return meals
    }
}
