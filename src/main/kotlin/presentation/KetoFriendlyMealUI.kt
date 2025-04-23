package org.example.presentation

import logic.usecase.SuggestKetoMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class KetoFriendlyMealUI(private val useCase: SuggestKetoMealUseCase, private val viewer: Viewer,private val reader: Reader) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {

        try {
            var suggestedMeal = useCase.execute()
            while (true) {
                viewer.log("Suggested Keto Meal: ${suggestedMeal.name}")
                viewer.log("Would you like to see more details or get another suggestion? (y/n): ")

                val userInput = reader.readInput()?.lowercase()
                if (userInput == "y") {
                    MealPresenter.printDetails(suggestedMeal)
                    break
                } else if (userInput == "n") {
                    suggestedMeal = useCase.execute()
                } else viewer.log("Invalid input. Please enter 'y' or 'n'.")
            }
        } catch (_: EmptyMealsException) {
            viewer.log("No meals in the database.")
        } catch (_: NoMealFoundException) {
            viewer.log("There is no more unique keto Meals")
        } catch (_: Exception) {
            viewer.log("There is a problem happened when retrieving the data")
        }
    }

    companion object {
        const val FEATURE_ID = 7
        const val FEATURE_NAME = "Keto diet meal helper"
    }
}
