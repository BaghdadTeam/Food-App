package org.example.presentation

import logic.usecase.SeaFoodMealUseCase

class SeafoodMealsUI(
    private val useCase: SeaFoodMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        useCase.execute()
    }

    companion object {
        const val FEATURE_ID = 14
        const val FEATURE_NAME = "Show seafood meals sorted by protein"
    }
}