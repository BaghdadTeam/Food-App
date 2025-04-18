package org.example.presentation

import logic.usecase.IraqiMealsIdentifierUseCase

class IraqiMealsUI(private val useCase: IraqiMealsIdentifierUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val meals = useCase.execute()
        println(
            if (meals.isEmpty()) "No Iraqi meals found." else "Iraqi Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }

    companion object {
        const val FEATURE_ID = 3
        const val FEATURE_NAME = "Identify Iraqi Meals"
    }
}