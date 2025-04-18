package logic.feature

import logic.use_case.SearchFoodByDateUseCase
import java.time.format.DateTimeFormatter
import java.util.*

class SearchFoodByDateFeature(
    private val useCase: SearchFoodByDateUseCase
) : Feature {
    override val number: Int = 8
    override val name: String = "Search foods by add date"

    private val scanner = Scanner(System.`in`)
    private val displayFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    override fun execute() {
        // Implement your logic here
        invoke()
    }

    private fun invoke() {
        print("Enter date to search meals (M/d/yyyy): ")
        val dateInput = scanner.nextLine()

        try {
            val meals = useCase.execute(dateInput)

            println("\nMeals added on ${meals.first().addDate.format(displayFormatter)}:")
            meals.forEach { println("${it.id}: ${it.name}") }

            print("\nEnter meal ID to view details (or 0 to exit): ")
            val input = scanner.nextLine()

            if (input != "0") {
                val mealId = input.toIntOrNull()
                val selectedMeal = meals.find { it.id == mealId }

                if (selectedMeal != null) {
                    displayMealDetails(selectedMeal)
                } else {
                    println("Meal with ID $input not found.")
                }
            }

        } catch (e: SearchFoodByDateUseCase.InvalidDateFormatException) {
            println("Error: ${e.message}")
        } catch (e: SearchFoodByDateUseCase.NoMealsFoundException) {
            println("Notice: ${e.message}")
        }
    }

    private fun displayMealDetails(meal: model.Meal) {
        println("\n=== Meal Details ===")
        println("ID: ${meal.id}")
        println("Name: ${meal.name}")
        println("Description: ${meal.description}")
        println("Date Added: ${meal.addDate.format(displayFormatter)}")
        println("Ingredients: ${meal.ingredients?.joinToString()}")
        println("Steps: ${meal.steps.joinToString()}")
        println("Prep Time: ${meal.preparationTime} mins")
    }
}
