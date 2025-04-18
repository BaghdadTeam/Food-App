package org.example.utils

import model.Meal

object MealPresenter {
    fun printDetails(meal: Meal){
        println("Meal Name: ${meal.name}")
        println("Preparation Time: ${meal.preparationTime}")
        printIngredients(meal)
        printNutritionInfo(meal)
    }
    private fun printNutritionInfo(meal : Meal){
        println(
            "Nutrition Info: ${meal.name?.uppercase()} contains:\n" +
                    " - Total Fat: ${meal.nutrition?.totalFat}g\n" +
                    " - Carbohydrates: ${meal.nutrition?.carbohydrates}g\n" +
                    " - Sugar: ${meal.nutrition?.sugar}g\n" +
                    " - Protein: ${meal.nutrition?.protein}g"
        )
    }
    private fun printIngredients(meal: Meal){
        meal.ingredients?.forEachIndexed { index, ingredient ->
            println("$index - $ingredient")
        }

    }
}