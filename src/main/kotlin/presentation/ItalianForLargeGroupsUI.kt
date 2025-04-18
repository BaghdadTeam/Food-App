package org.example.presentation

import logic.usecase.MealsForLargeGroupUseCase

class ItalianForLargeGroupsUI(
    private val useCase: MealsForLargeGroupUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val meals = useCase.execute()
        println(
            if (meals.isEmpty()) "No meals for large group found matching "
            else "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }

    companion object {
        const val FEATURE_ID = 15
        const val FEATURE_NAME = "Italian food suggestions for large groups"
    }
}
