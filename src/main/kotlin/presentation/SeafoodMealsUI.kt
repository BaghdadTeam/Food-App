package org.example.presentation

import logic.usecase.SeaFoodMealUseCase

class SeafoodMealsUI(
    private val useCase: SeaFoodMealUseCase
) : Feature {
    override val number: Int = 14
    override val name: String = "Show seafood meals sorted by protein"

    override fun execute() {
        useCase.execute()
    }
}
