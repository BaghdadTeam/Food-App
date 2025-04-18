package org.example.presentation

import logic.usecase.SearchMealUseCase
import org.example.utils.EmptyMealName
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class MealSearchUI(private val useCase: SearchMealUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        print("Enter meal name (partial or full): ")
        val keyword = readlnOrNull()?.trim().orEmpty()
        try {
            val meals = useCase.execute(keyword)
            println("Matching Meals:\n${meals.joinToString("\n") { "- ${it.name} " }}")

        } catch (_: EmptyMealName) {
            println("Meal name should not be empty")
        } catch (_: NoElementMatch) {
            println("No meals found matching '$keyword'.")
        } catch (_: EmptyMeals) {
            println("No meals in the database.")
        } catch (_: Exception) {
            println("There is a problem happened when retrieving the data.")
        }
    }

    companion object {
        const val FEATURE_ID = 2
        const val FEATURE_NAME = "Search meal by name"
    }
}