package org.example.presentation

import logic.usecase.SuggestHighCalorieMealUseCase

class HighCalorieMealsUI(
    private val useCase: SuggestHighCalorieMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        useCase.execute()
    }

    companion object {
        const val FEATURE_ID = 13
        const val FEATURE_NAME = "So thin problem"
    }
}
