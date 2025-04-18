package org.example.presentation

import logic.usecase.GuessMealGameUseCase

class GuessGameUI(private val useCase: GuessMealGameUseCase) : Feature {
    override val number: Int = 5
    override val name: String = "Guess game"

    override fun execute() {
        useCase.guessMealPreparationTime()
    }
}