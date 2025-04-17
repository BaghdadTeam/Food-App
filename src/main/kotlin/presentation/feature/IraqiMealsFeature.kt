package presentation.feature

import logic.use_case.IraqiMealsIdentifierUseCase

class IraqiMealsFeature(private val useCase: IraqiMealsIdentifierUseCase) : Feature {
    override val number: Int = 3
    override val name: String = "Identify Iraqi meals"

    override fun execute() {
        val meals = useCase.getIraqiMeals()
        println(
            if (meals.isEmpty()) "No Iraqi meals found." else "Iraqi Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }
}