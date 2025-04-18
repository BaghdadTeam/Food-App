import logic.MealsProvider
import model.Meal
import kotlinx.datetime.LocalDate

class SearchFoodByDateUseCase(private val mealsProvider: MealsProvider) {

    class InvalidDateFormatException(message: String) : Exception(message)
    class NoMealsFoundException(message: String) : Exception(message)

    // Parses M/d/yyyy format manually
    private fun parseDate(input: String): LocalDate {
        val parts = input.split("/")
        if (parts.size != 3) throw InvalidDateFormatException("Invalid date format. Please use M/d/yyyy (e.g., 4/11/2024).")
        return try {
            val month = parts[0].toInt()
            val day = parts[1].toInt()
            val year = parts[2].toInt()
            LocalDate(year, month, day)
        } catch (e: Exception) {
            throw InvalidDateFormatException("Invalid date numbers. Please use M/d/yyyy (e.g., 4/11/2024).")
        }
    }

    fun execute(dateInput: String): List<Meal> {
        val searchDate = parseDate(dateInput)

        val meals = mealsProvider.getMeals().filter { it.addDate == searchDate }

        if (meals.isEmpty()) {
            throw NoMealsFoundException("No meals found for date: $dateInput")
        }

        return meals
    }
}
