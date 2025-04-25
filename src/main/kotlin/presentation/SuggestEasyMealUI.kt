package org.example.presentation

import logic.usecase.SuggestEasyMealUseCase

class SuggestEasyMealUI(
    private val useCase: SuggestEasyMealUseCase,
    private val viewer: Viewer,
    private val mealTablePrinterFactory: () -> MealTablePrinter = { MealTablePrinter(viewer) }
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val meals = useCase.execute()

            if (meals.isEmpty()) {
                viewer.log("No easy meals available.")
            } else {
                val printer = mealTablePrinterFactory()
                printer.print(meals, isGymHelperUI = false)
            }
        } catch (e: Exception) {
            viewer.log("Error: ${e.message}")
        }
    }

    companion object {
        const val FEATURE_ID = 4
        const val FEATURE_NAME = "Suggest easy meal"
    }
}

