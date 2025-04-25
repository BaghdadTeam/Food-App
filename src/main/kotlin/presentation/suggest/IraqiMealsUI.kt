package org.example.presentation.suggest

import org.example.logic.usecase.suggest.IraqiMealsIdentifierUseCase
import model.Meal
import org.example.presentation.Feature
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class IraqiMealsUI(
    private val useCase: IraqiMealsIdentifierUseCase,
    private val viewer: Viewer
) : Feature {

    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val meals = useCase.execute()
            printMealsTable(meals)
        } catch (e: EmptyMealsException) {
            viewer.log("There is no meals in database.")
        } catch (e: NoMealFoundException) {
            viewer.log("There is no Iraqi meals found")
        } catch (e: Exception) {
            viewer.log("Something wrong happened while retrieving the data.")
        }
    }

    private fun printMealsTable(meals: List<Meal>) {
        viewer.log(String.run { format("%-10s    |  %-20s", "ID", "Name") })
        meals.forEach { meal ->
            viewer.log(
                String.format(
                    "%-10s    |  %-20s",
                    meal.id, meal.name,
                )
            )
        }
    }

    companion object {
        const val FEATURE_ID = 3
        const val FEATURE_NAME = "Identify Iraqi Meals"
    }
}