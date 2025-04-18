package org.example.presentation

import logic.usecase.IraqiMealsIdentifierUseCase

class IraqiMealsUI(private val useCase: IraqiMealsIdentifierUseCase) : Feature {
    override val number: Int = 3
    override val name: String = "Identify Iraqi meals"

    override fun execute() {
        val meals = useCase.execute()
        println(
            if (meals.isEmpty()) "No Iraqi meals found." else "Iraqi Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }
}