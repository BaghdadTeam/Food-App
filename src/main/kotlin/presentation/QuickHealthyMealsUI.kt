package org.example.presentation

import logic.usecase.FilterQuickHealthyMealsUseCase

class QuickHealthyMealsUI(private val useCase: FilterQuickHealthyMealsUseCase) : Feature {
    override val number: Int = 1
    override val name: String = "Get healthy fast food meals"

    override fun execute() {
        val meals = useCase.execute(10)
        println(
            if (meals.isEmpty()) "No quick healthy meals found." else "Quick & Healthy Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }

}
