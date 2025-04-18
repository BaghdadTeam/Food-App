package org.example.presentation

import logic.usecase.IraqiMealsIdentifierUseCase
import model.Meal

class IraqiMealsUI(private val useCase: IraqiMealsIdentifierUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val meals = useCase.execute()
        try {
            printMealsTable(meals)
        } catch (e: NoSuchElementException) {
            println("There is no Iraqi meals found")
        } catch (e: Exception) {
            println(
                """Something wrong happened when retriveing the data.
                |please try again later
            """.trimMargin()
            )
        }
    }

    private fun printMealsTable(meals: List<Meal>) {
        println(String.run { format("%-10s    |  %-20s", "ID", "Name") })
        meals.forEach { meal ->
            println(
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