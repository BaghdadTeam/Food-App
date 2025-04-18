package org.example.presentation

import logic.usecase.EggFreeSweetsUseCase

class SweetsWithNoEggsUI(private val useCase: EggFreeSweetsUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        println("Suggested Sweet: ${useCase.execute()}")
    }

    companion object {
        const val FEATURE_ID = 6
        const val FEATURE_NAME = "Sweets with no eggs"
    }
}
