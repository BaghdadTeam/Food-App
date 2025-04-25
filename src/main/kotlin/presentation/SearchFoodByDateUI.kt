package org.example.presentation

import kotlinx.datetime.LocalDate
import logic.usecase.SearchFoodByDateUseCase
import org.example.utils.InvalidDateFormatException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class SearchFoodByDateUI(
    private val useCase: SearchFoodByDateUseCase, private val viewer: Viewer,private val reader: Reader
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        // Implement your logic here
        invoke()
    }

    private fun invoke() {
        viewer.log("Enter date to search meals (yyyy-m-d): ")
        val dateInput = reader.readInput()?:""

        try {
            val meals = useCase.execute(dateInput)

            viewer.log("\nMeals added on ${LocalDate.parse(dateInput)}:")
            meals.forEach { viewer.log("${it.id}: ${it.name}") }

            viewer.log("\nEnter meal ID to view details (or 0 to exit): ")
            val input = reader.readInput()

            if (input != "0") {
                val mealId = input?.toIntOrNull()
                val selectedMeal = meals.find { it.id == mealId }

                if (selectedMeal != null) {
                    displayMealDetails(selectedMeal)
                } else {
                    viewer.log("Meal with ID $input not found.")
                }
            }

        } catch (e: InvalidDateFormatException) {
            viewer.log("Error: ${e.message}")
        } catch (e: NoMealFoundException) {
            viewer.log("Notice: ${e.message}")
        }
    }

    private fun displayMealDetails(meal: model.Meal) {
        viewer.log("\n=== Meal Details ===")
        viewer.log("Date Added: ${meal.date}")
        MealPresenter.printDetails(meal)
    }

    companion object {
        const val FEATURE_ID = 8
        const val FEATURE_NAME = "Search foods by add date"
    }
}
