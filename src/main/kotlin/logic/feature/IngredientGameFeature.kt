package logic.feature

import logic.use_case.IngredientGameUseCase

class IngredientGameFeature(private val useCase: IngredientGameUseCase) : Feature {
    override val number: Int = 11
    override val name: String = "Ingredient game"

    override fun execute() {
        useCase.playGame()
    }
}
