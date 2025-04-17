package logic.feature

import org.example.logic.use_case.SuggestHighCalorieMealUseCase

class HighCalorieMealsFeature(
    private val useCase: SuggestHighCalorieMealUseCase
) : Feature {
    override val number: Int = 13
    override val name: String = "So thin problem"

    override fun execute() {
        useCase.suggestMeal()
    }
}
