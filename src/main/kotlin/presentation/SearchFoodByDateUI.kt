package org.example.presentation

import kotlinx.datetime.LocalDate
import logic.usecase.SearchFoodByDateUseCase
import org.example.utils.InvalidDateFormatException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class SearchFoodByDateUI(
    private val useCase: SearchFoodByDateUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        // Implement your logic here
        invoke()
    }

    private fun invoke() {
        print("Enter date to search meals (yyyy-m-d): ")
        val dateInput = readln()

        try {
            val meals = useCase.execute(dateInput)

            println("\nMeals added on ${LocalDate.parse(dateInput)}:")
            meals.forEach { println("${it.id}: ${it.name}") }

            print("\nEnter meal ID to view details (or 0 to exit): ")
            val input = readln()

            if (input != "0") {
                val mealId = input.toIntOrNull()
                val selectedMeal = meals.find { it.id == mealId }

                if (selectedMeal != null) {
                    displayMealDetails(selectedMeal)
                } else {
                    println("Meal with ID $input not found.")
                }
            }

        } catch (e: InvalidDateFormatException) {
            println("Error: ${e.message}")
        } catch (e: NoMealFoundException) {
            println("Notice: ${e.message}")
        }
    }

    private fun displayMealDetails(meal: model.Meal) {
        println("\n=== Meal Details ===")
        println("Date Added: ${meal.date}")
        MealPresenter.printDetails(meal)
    }

    companion object {
        const val FEATURE_ID = 8
        const val FEATURE_NAME = "Search foods by add date"
    }
}
