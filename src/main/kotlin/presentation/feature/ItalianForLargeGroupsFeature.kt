package presentation.feature

import logic.use_case.MealsForLargeGroupUseCase

class ItalianForLargeGroupsFeature(
    private val useCase: MealsForLargeGroupUseCase
) : Feature {
    override val number: Int = 15
    override val name: String = "Italian food suggestions for large groups"

    override fun execute() {
        val meals = useCase.execute()
        println(
            if (meals.isEmpty()) "No meals for large group found matching "
            else "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }
}
