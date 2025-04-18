package org.example.presentation

import logic.usecase.SearchMealUseCase

class MealSearchUI(private val useCase: SearchMealUseCase) : Feature {
    override val number: Int = 2
    override val name: String = "Search meal by name"

    override fun execute() {
        print("Enter meal name (partial or full): ")
        val keyword = readlnOrNull()?.trim().orEmpty()
        val meals = useCase.search(keyword)
        println(
            if (meals.isEmpty()) "No meals found matching '$keyword'." else "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }
}
