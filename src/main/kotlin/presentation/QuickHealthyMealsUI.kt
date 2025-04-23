package org.example.presentation

import logic.usecase.FilterQuickHealthyMealsUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class QuickHealthyMealsUI(private val useCase: FilterQuickHealthyMealsUseCase, private val viewer: Viewer,private val reader: Reader) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {

        viewer.log("Please enter the number of meals you want to see:")
        val countInput = reader.readInput()?.toIntOrNull()

        if (countInput == null || countInput <= 0) {
            viewer.log("Please enter a valid positive number.")
        } else {
            try {
                val meals = useCase.execute(countInput)
                if (meals.isEmpty()) {
                    viewer.log("No quick healthy meals found ")
                } else {
                    viewer.log("Quick & Healthy Meals:")
                    meals.forEach { viewer.log("- ${it.name}") }
                }
            } catch (_: EmptyMealsException) {
                viewer.log("There is no meals in database")
            } catch (_: NoMealFoundException) {
                viewer.log("There are no quick healthy meals available ")
            } catch (_: Exception) {
                viewer.log("There is a problem happened when retrieving the data.")
            }
        }
    }

    companion object {
        const val FEATURE_ID = 1
        const val FEATURE_NAME = "Get healthy fast food meals"
    }
}