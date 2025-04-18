package org.example.presentation

import logic.usecase.IngredientGameUseCase

class IngredientGameUI(private val useCase: IngredientGameUseCase) : Feature {
    override val number: Int = 11
    override val name: String = "Ingredient game"

    override fun execute() {
        useCase.playGame()
    }
}
