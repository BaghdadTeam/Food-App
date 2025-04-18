package org.example.presentation

import logic.usecase.SuggestKetoMealUseCase

class KetoFriendlyMealUI(private val useCase: SuggestKetoMealUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        println("Suggested Keto Meal: ${useCase.execute()}")
    }

    companion object {
        const val FEATURE_ID = 7
        const val FEATURE_NAME = "Keto diet meal helper"
    }
}
