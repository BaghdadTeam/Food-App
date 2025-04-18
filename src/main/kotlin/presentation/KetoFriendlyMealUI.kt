package org.example.presentation

import logic.usecase.SuggestKetoMealUseCase
import org.example.utils.EmptyMeals
import org.example.utils.MealPresenter
import org.example.utils.NoElementMatch

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
                    MealPresenter.printDetails(suggestedMeal)
                    break
                } else if (userInput == "n") {
                    suggestedMeal = useCase.execute()
                } else println("Invalid input. Please enter 'y' or 'n'.")
            }
        } catch (_: EmptyMeals) {
            println("No meals in the database.")
        } catch (e: NoElementMatch) {
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
