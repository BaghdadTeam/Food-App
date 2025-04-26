package org.example.presentation

import model.Meal

class MealTablePrinter(private val viewer: Viewer) : MealPrinter {

    private val RESET = "\u001B[0m"
    private val HEADER_COLOR = "\u001B[44;97m"
    private val NAME_ROW_COLOR = "\u001B[47;30m"
    private val VALUE_ROW_COLOR = "\u001B[106;30m"

    private fun String.pad(width: Int) = this.padEnd(width)

    private fun printLine(columnWidths: List<Int>) {
        val line = columnWidths.joinToString("+", prefix = "+", postfix = "+") { "-".repeat(it + 2) }
        viewer.log(line)
    }

    private fun printHeader(headers: List<String>, widths: List<Int>) {
        val headerRow = headers.mapIndexed { i, header -> " ${header.pad(widths[i])} " }
            .joinToString("|", prefix = "|", postfix = "|")
        viewer.log(HEADER_COLOR + headerRow + RESET)
        printLine(widths)
    }

    override fun print(meals: List<Meal>, isGymHelperUI: Boolean) {
        if (meals.isEmpty()) {
            viewer.log("No meals to show.")
            return
        }

        val headers: List<String>
        val columnWidths: List<Int>

        if (isGymHelperUI) {
            headers = listOf("ID", "Meal Name", "Calories", "Protein")
            columnWidths = listOf(4, 24, 11, 11)
        } else {
            headers = listOf("ID", "Meal Name", "Preparation Time", "Ingredients", "Steps")
            columnWidths = listOf(4, 24, 17, 13, 7)
        }

        val rows = meals.mapIndexed { index, meal ->
            val id = "${index + 1}".pad(4)
            val name = (meal.name ?: "N/A").pad(24)

            if (isGymHelperUI) {
                val calories = (meal.nutrition?.calories?.toString() ?: "N/A").pad(11)
                val protein = (meal.nutrition?.protein?.toString() ?: "N/A").pad(11)
                listOf(id, name, calories, protein)
            } else {
                val time = "${meal.preparationTime} min".pad(17)
                val ingredients = (meal.ingredients?.size?.toString() ?: "0").pad(13)
                val steps = meal.steps.size.toString().pad(7)
                listOf(id, name, time, ingredients, steps)
            }
        }

        printHeader(headers, columnWidths)

        rows.forEachIndexed { index, row ->
            val color = if (index % 2 == 0) NAME_ROW_COLOR else VALUE_ROW_COLOR
            val formattedRow = row.joinToString("|", prefix = "|", postfix = "|") { " $it " }
            viewer.log(color + formattedRow + RESET)
            printLine(columnWidths)
        }
    }
}
