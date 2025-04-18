package org.example.presentation

import logic.usecase.SeaFoodMealUseCase

class SeafoodMealsUI(
    private val useCase: SeaFoodMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val rankedMeals = useCase.execute().take(NUMBER_OF_ITEMS)
        println("Rank    Name    Protein")
        rankedMeals.forEach { (rank, meal) ->
            println("$rank      ${meal.name}     ${meal.nutrition!!.protein}")
        }
    }

    companion object {
        const val FEATURE_ID = 14
        const val FEATURE_NAME = "Show seafood meals sorted by protein"
        const val NUMBER_OF_ITEMS = 50
    }
}