package logic.feature

import logic.use_case.GuessMealGameUseCase

class GuessGameFeature(private val useCase: GuessMealGameUseCase) : Feature {
    override val number: Int = 5
    override val name: String = "Guess game"

    override fun execute() {
        useCase.guessMealPreparationTime()
    }
}