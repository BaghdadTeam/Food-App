package org.example.presentation

import logic.usecase.SuggestHighCalorieMealUseCase

class HighCalorieMealsUI(
    private val useCase: SuggestHighCalorieMealUseCase
) : Feature {
    override val number: Int = 13
    override val name: String = "So thin problem"

    override fun execute() {
        useCase.execute()
    }
}
