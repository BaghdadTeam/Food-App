package presentation.feature

import logic.use_case.SuggestKetoMealUseCase

class KetoFriendlyMealFeature(private val useCase: SuggestKetoMealUseCase) : Feature {
    override val number: Int = 7
    override val name: String = "Keto diet meal helper"

    override fun execute() {
        println("Suggested Keto Meal: ${useCase.getKetoMealSuggest()}")
    }
}
