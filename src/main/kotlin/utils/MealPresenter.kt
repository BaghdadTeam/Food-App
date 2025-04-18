package org.example.utils

import model.Meal

object MealPresenter {
    fun printDetails(meal: Meal) {
        println("Meal Name: ${meal.name}")
        println("Preparation Time: ${meal.preparationTime}")
        printNutritionInfo(meal)
        println("Ingredients:")
        printIngredients(meal)
        println("steps:")
        printSteps(meal)
    }

    private fun printNutritionInfo(meal: Meal) {
        println(
            """Nutrition contains:
            | - Total Fat: ${meal.nutrition?.totalFat}g
            | - Carbohydrates: ${meal.nutrition?.carbohydrates}g
            | - Sugar: ${meal.nutrition?.sugar}g
            | - Protein: ${meal.nutrition?.protein}g
        """.trimMargin()
        )
    }

    private fun printIngredients(meal: Meal) {
        meal.ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1} - $ingredient")
        }

    }

    private fun printSteps(meal: Meal) {
        meal.steps.forEachIndexed { index, step ->
            println("${index + 1} - $step")
        }
    }
}