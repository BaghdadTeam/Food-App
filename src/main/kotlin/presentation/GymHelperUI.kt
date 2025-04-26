package org.example.presentation

import logic.usecase.filter.GymMealHelperUseCase

class GymHelperUI(
    private val useCase: GymMealHelperUseCase,
    private val viewer: Viewer,
    private val reader: Reader,
    private val mealTablePrinterFactory: () -> MealTablePrinter = { MealTablePrinter(viewer) }
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            viewer.log(" Enter desired calories: ")
            val calories = reader.readInput()?.toDoubleOrNull() ?: throw Exception("Invalid number")

            viewer.log(" Enter desired protein : ")
            val protein = reader.readInput()?.toDoubleOrNull() ?: throw Exception("Invalid number")

            val matchingMeals = useCase.getGymMealsSuggestion(calories, protein)

            if (matchingMeals.isEmpty()) {
                viewer.log("No meals found matching your nutritional goals.")
            } else {
                viewer.log("\n Meals matching your gym goals:\n")
                val printer = mealTablePrinterFactory()
                printer.print(matchingMeals, isGymHelperUI = true)
            }
        } catch (e: Exception) {
            viewer.log("Error: ${e.message}")
        }
    }

    companion object {
        const val FEATURE_ID = 9
        const val FEATURE_NAME = "Gym helper"
    }
}
//