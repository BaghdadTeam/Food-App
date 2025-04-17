package logic.feature

import logic.use_case.SeaFoodMealUseCase

class SeafoodMealsFeature(
    private val useCase: SeaFoodMealUseCase
) : Feature {
    override val number: Int = 14
    override val name: String = "Show seafood meals sorted by protein"

    override fun execute() {
        // Implement your logic here
    }
}
