package org.example.presentation

import logic.usecase.SearchMealUseCase
import org.example.utils.EmptyMealNameException
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class MealSearchUI(private val useCase: SearchMealUseCase, private val viewer: Viewer,private val reader: Reader) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        viewer.log("Enter meal name (partial or full): ")
        val keyword = reader.readInput()?.trim().orEmpty()
        try {
            val meals = useCase.execute(keyword)
            viewer.log("Matching Meals:\n${meals.joinToString("\n") { "- ${it.name} " }}")

        } catch (_: EmptyMealsException) {
            viewer.log("No meals in the database.")
        } catch (_: EmptyMealNameException) {
            viewer.log("Meal name should not be empty")
        } catch (_: NoMealFoundException) {
            viewer.log("No meals found matching '$keyword'.")
        } catch (_: Exception) {
            viewer.log("There is a problem happened when retrieving the data.")
        }
    }

    companion object {
        const val FEATURE_ID = 2
        const val FEATURE_NAME = "Search meal by name"
    }
}