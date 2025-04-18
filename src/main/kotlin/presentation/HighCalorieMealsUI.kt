package org.example.presentation

import logic.usecase.SuggestHighCalorieMealUseCase
import model.Meal

class HighCalorieMealsUI(
    private val useCase: SuggestHighCalorieMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME
    override fun execute() {
        var likeIt: Boolean
        var meal: Meal
        do {

            try {
                meal = useCase.execute()
            } catch (exception : NoSuchElementException) {
                println("There is no more high calorie meals")
                return
            }

            println("Meal Name : ${meal.name}")
            println("Do you like this meal?\n1-yes\n2-no")
            print("Enter your choice:")
            val input = readln()

            if (input == "1") {
                println(meal.description)
                likeIt = true
            } else if (input == "2") {
                likeIt = false
            } else {
                break
            }

        } while (!likeIt)

    }


    companion object {
        const val FEATURE_ID = 13
        const val FEATURE_NAME = "Suggest high calorie meal"
    }
}
