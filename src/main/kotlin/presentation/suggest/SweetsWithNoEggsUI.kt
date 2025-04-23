package org.example.presentation.suggest

import org.example.logic.usecase.suggest.EggFreeSweetsUseCase
import org.example.presentation.Feature
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class SweetsWithNoEggsUI(private val useCase: EggFreeSweetsUseCase, private val viewer: Viewer, private val reader: Reader) :
    Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            while (true) {
                val sweet = useCase.execute()
                viewer.log("How about this sweet: ${sweet.name}")
                viewer.log("Do you like it? (yes/no): ")
                val choice = reader.readInput()?.trim()
                if (choice?.contains("y", ignoreCase = true) ?: false) {
                    MealPresenter.printDetails(meal = sweet)
                    break
                }
            }
        } catch (e: EmptyMealsException) {
            viewer.log("There is no meals in database")
        } catch (e: NoMealFoundException) {
            viewer.log("There is no egg free sweets found")
        } catch (e: Exception) {
            viewer.log(
                """Something wrong happened when retrieving the data.
                |please try again later
            """.trimMargin()
            )
        }
    }

    companion object {
        const val FEATURE_ID = 6
        const val FEATURE_NAME = "Sweets with no eggs"
    }
}
