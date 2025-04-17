package logic.feature

import logic.use_case.EggFreeSweetsUseCase

class SweetsWithNoEggsFeature(private val useCase: EggFreeSweetsUseCase) : Feature {
    override val number: Int = 6
    override val name: String = "Sweets with no eggs"

    override fun execute() {
        println("Suggested Sweet: ${useCase.suggestSweet()}")
    }
}
