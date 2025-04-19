package org.example.presentation

import logic.usecase.SuggestEasyMealUseCase
import model.Meal

class SuggestEasyMealUI(private val useCase: SuggestEasyMealUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME


    override fun execute() {
        val easyMeals = useCase.execute()
        val printer = MealTablePrinter()
        printer.print(easyMeals)

    }

    companion object {
        const val FEATURE_ID = 4
        const val FEATURE_NAME = "Suggest easy meal"
    }
}


class MealTablePrinter {

    private val RESET = "\u001B[0m"
    private val BLUE = "\u001B[34m"
    private val GRAY_BG = "\u001B[47m"
    private val GREEN_BG = "\u001B[102m"
    private val WHITE_TEXT = "\u001B[30m"


    private val indexWidth = 4
    private val nameWidth = 22
    private val timeWidth = 8
    private val ingredientsWidth = 13
    private val stepsWidth = 7

    private fun String.pad(width: Int) = this.padEnd(width, ' ')

    private fun printLine() {
        println(
            "+${"-".repeat(indexWidth)}+${"-".repeat(nameWidth)}+${"-".repeat(timeWidth)}+${
                "-".repeat(
                    ingredientsWidth
                )
            }+${"-".repeat(stepsWidth)}+"
        )
    }

    private fun printHeader() {
        printLine()
        println(
            BLUE +
                    "| ${"#".pad(indexWidth - 1)}" +
                    "| ${"Name".pad(nameWidth - 1)}" +
                    "| ${"Time".pad(timeWidth - 1)}" +
                    "| ${"Ingredients".pad(ingredientsWidth - 1)}" +
                    "| ${"Steps".pad(stepsWidth - 1)}|" +
                    RESET
        )
        printLine()
    }

    private fun printRow(index: Int, meal: Meal, isEven: Boolean) {
        val idx = (index + 1).toString().pad(indexWidth - 1)
        val name = meal.name?.pad(nameWidth - 1)
        val time = "${meal.preparationTime} min".pad(timeWidth - 1)
        val ingredients = (meal.ingredients?.size ?: 0).toString().pad(ingredientsWidth - 1)
        val steps = meal.steps.size.toString().pad(stepsWidth - 1)

        val row = "| $idx| $name| $time| $ingredients| $steps|"

        val bgColor = if (isEven) GRAY_BG else GREEN_BG
        println(bgColor + WHITE_TEXT + row + RESET)
    }

    fun print(meals: List<Meal>) {
        if (meals.isEmpty()) {
            println("No meals to show.")
            return
        }

        printHeader()
        meals.forEachIndexed { i, meal ->
            printRow(i, meal, isEven = i % 2 == 0)
        }
        printLine()
    }
}
