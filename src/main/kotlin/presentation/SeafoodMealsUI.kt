package org.example.presentation

import logic.usecase.SeaFoodMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class SeafoodMealsUI(
    private val useCase: SeaFoodMealUseCase, private val viewer: Viewer
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val rankedMeals = useCase.execute()
            viewer.log("Rank    Name    Protein")
            rankedMeals.forEach { (rank, meal) ->
                viewer.log("$rank      ${meal.name}     ${meal.nutrition!!.protein}")
            }
        } catch (e: EmptyMealsException) {
            viewer.log("There is no meals in database")
        } catch (e: NoMealFoundException) {
            viewer.log("There is no seafood meals found")
        } catch (e: Exception) {
            viewer.log("There is a problem happened when retrieving the data.")
        }
    }

    companion object {
        const val FEATURE_ID = 14
        const val FEATURE_NAME = "Show seafood meals sorted by protein"
    }
}