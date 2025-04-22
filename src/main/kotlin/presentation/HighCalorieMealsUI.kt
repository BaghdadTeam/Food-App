package org.example.presentation

import logic.usecase.SuggestHighCalorieMealUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class HighCalorieMealsUI(
    private val useCase: SuggestHighCalorieMealUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME
    override fun execute() {

        try {
            var meal = useCase.execute()
            while (true) {
                println("Meal Name : ${meal.name}")
                println("Would you like to see the description or get another suggestion? (y/n): ")
                print("Enter your choice:")
                val input = readln().lowercase()

                if (input == "y") {
                    MealPresenter.printDetails(meal)
                    break
                } else if (input == "n") {
                    try {
                        meal = useCase.execute()
                    } catch (exception: NoSuchElementException) {
                        println("There is no more high calorie meals")
                        break
                    }
                } else {
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
            }
        } catch (_: EmptyMealsException) {
            println("No meals in the database.")
        } catch (e: NoMealFoundException) {
            println(
                """There is no high calories meal at the moment
                |please try again later.
            """.trimMargin()
            )

        } catch (e: Exception) {
            println("There something wrong happened when retrieving the data")
        }
    }


    companion object {
        const val FEATURE_ID = 13
        const val FEATURE_NAME = "Suggest high calorie meal"
    }
}
