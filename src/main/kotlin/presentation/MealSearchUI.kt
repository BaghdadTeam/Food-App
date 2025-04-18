package org.example.presentation

import logic.usecase.SearchMealUseCase

class MealSearchUI(private val useCase: SearchMealUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        print("Enter meal name (partial or full): ")
        val keyword = readlnOrNull()?.trim().orEmpty()
        if (keyword.isEmpty()) {
            println("Meal name should not be empty !!!")
            return
        }
        val meals = useCase.execute(keyword)
        println(
            if (meals.isEmpty()) "No meals found matching '$keyword'." else "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }

    companion object {
        const val FEATURE_ID = 2
        const val FEATURE_NAME = "Search meal by name"
    }
}
