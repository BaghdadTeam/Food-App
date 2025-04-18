package org.example.presentation

import logic.usecase.EggFreeSweetsUseCase

class SweetsWithNoEggsUI(private val useCase: EggFreeSweetsUseCase) : Feature {
    override val number: Int = 6
    override val name: String = "Sweets with no eggs"

    override fun execute() {
        println("Suggested Sweet: ${useCase.suggestSweet()}")
    }
}
