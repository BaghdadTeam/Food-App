package org.example.presentation

import logic.usecase.SuggestKetoMealUseCase

class KetoFriendlyMealUI(private val useCase: SuggestKetoMealUseCase) : Feature {
    override val number: Int = 7
    override val name: String = "Keto diet meal helper"

    override fun execute() {
        println("Suggested Keto Meal: ${useCase.execute()}")
    }
}
