package presentation.feature

import logic.use_case.SuggestKetoMealUseCase

class KetoFriendlyMealFeature(private val useCase: SuggestKetoMealUseCase) : Feature {
    override val number: Int = 7
    override val name: String = "Keto diet meal helper"

    override fun execute() {

        try {
        var suggestedMeal = useCase.getKetoMealSuggest()
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
                suggestedMeal = useCase.getKetoMealSuggest()
            } else println("Invalid input. Please enter 'y' or 'n'.")
        }
        }
        catch (e: NoSuchElementException) {
            println("There is no more unique keto Meals")
        }
    }
}
