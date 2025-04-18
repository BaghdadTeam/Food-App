package org.example.presentation

import logic.usecase.SuggestKetoMealUseCase

class KetoFriendlyMealUI(private val useCase: SuggestKetoMealUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {

        try {
            var suggestedMeal = useCase.execute()
            while (true) {
                println("Suggested Keto Meal: ${suggestedMeal.name}")
                println("Would you like to see more details or get another suggestion? (y/n): ")

                val userInput = readln().lowercase()
                if (userInput == "y") {
                    println("Meal Name: ${suggestedMeal.name}")
                    println("Preparation Time: ${suggestedMeal.preparationTime}")
                    println(
                        "Nutrition Info: ${suggestedMeal.name?.uppercase()} contains:\n" +
                                " - Total Fat: ${suggestedMeal.nutrition?.totalFat}g\n" +
                                " - Carbohydrates: ${suggestedMeal.nutrition?.carbohydrates}g\n" +
                                " - Sugar: ${suggestedMeal.nutrition?.sugar}g\n" +
                                " - Protein: ${suggestedMeal.nutrition?.protein}g"
                    )
                    break
                } else if (userInput == "n") {
                    suggestedMeal = useCase.execute()
                } else println("Invalid input. Please enter 'y' or 'n'.")
            }
        } catch (e: NoSuchElementException) {
            println("There is no more unique keto Meals")
        } catch (e: Exception) {
            println("There is a problem happened when retrieving the data")
        }
    }

    companion object {
        const val FEATURE_ID = 7
        const val FEATURE_NAME = "Keto diet meal helper"
    }
}
