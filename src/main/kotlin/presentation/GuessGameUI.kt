package org.example.presentation

import logic.usecase.GuessMealGameUseCase

class GuessGameUI(private val useCase: GuessMealGameUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        useCase.execute()
    }

    companion object {
        const val FEATURE_ID = 5
        const val FEATURE_NAME = "Guess Game"
    }
}