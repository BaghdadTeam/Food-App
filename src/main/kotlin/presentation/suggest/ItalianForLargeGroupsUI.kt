package org.example.presentation.suggest

import org.example.logic.usecase.suggest.MealsForLargeGroupUseCase
import org.example.presentation.Feature
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class ItalianForLargeGroupsUI(
    private val useCase: MealsForLargeGroupUseCase,
    private val viewer: Viewer
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val meals = useCase.execute()
            viewer.log("Matching Meals:")
            meals.forEachIndexed { index, meal ->
                viewer.log("${index + 1} - ${meal.name}")
            }
        } catch (_: EmptyMealsException) {
            viewer.log("No meals in the database.")
        } catch (e: NoMealFoundException) {
            viewer.log(
                """There is no meals for large group at the moment
                |please try again later
            """.trimMargin()
            )
        } catch (e: Exception) {
            viewer.log("There is something happened when retrieving data")
        }
    }

    companion object {
        const val FEATURE_ID = 15
        const val FEATURE_NAME = "Italian food suggestions for large groups"
    }
}