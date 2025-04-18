package org.example.presentation

import logic.usecase.SeaFoodMealUseCase

class SeafoodMealsUI(
    private val useCase: SeaFoodMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val rankedMeals = useCase.execute()
            println("Rank    Name    Protein")
            rankedMeals.forEach { (rank, meal) ->
                println("$rank      ${meal.name}     ${meal.nutrition!!.protein}")
            }
        } catch (e: NoSuchElementException) {
            println("There is no seafood meals found")
        } catch (e: Exception) {
            println("There is a problem happened when retrieving the data.")
        }
    }

    companion object {
        const val FEATURE_ID = 14
        const val FEATURE_NAME = "Show seafood meals sorted by protein"
    }
}