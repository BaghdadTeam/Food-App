package org.example.presentation

import logic.usecase.FilterQuickHealthyMealsUseCase

class QuickHealthyMealsUI(private val useCase: FilterQuickHealthyMealsUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val meals = useCase.execute(NUMBER_OF_MEALS)
        println(
            if (meals.isEmpty()) "No quick healthy meals found." else "Quick & Healthy Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }

    companion object {
        const val FEATURE_ID = 1
        const val FEATURE_NAME = "Get healthy fast food meals"
        const val NUMBER_OF_MEALS = 10
    }
}