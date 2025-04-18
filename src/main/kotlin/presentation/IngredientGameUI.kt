package org.example.presentation

import logic.usecase.IngredientGameUseCase

class IngredientGameUI(private val useCase: IngredientGameUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        useCase.execute()
    }

    companion object {
        const val FEATURE_ID = 11
        const val FEATURE_NAME = "Ingredient Game"
    }
}
