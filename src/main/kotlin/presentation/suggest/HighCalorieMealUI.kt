package org.example.presentation.suggest

import org.example.logic.usecase.suggest.SuggestHighCalorieMealUseCase
import org.example.presentation.Feature
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.example.utils.EmptyMealsException
import org.example.utils.MealPresenter
import org.example.utils.NoMealFoundException

class HighCalorieMealUI(
    private val useCase: SuggestHighCalorieMealUseCase, private val viewer: Viewer, private val reader: Reader
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME
    override fun execute() {

        try {
            var meal = useCase.execute()
            while (true) {
                viewer.log("Meal Name : ${meal.name}")
                viewer.log("Would you like to see the description or get another suggestion? (y/n): ")
                print("Enter your choice:")
                val input = reader.readInput()?.lowercase()

                if (input == "y") {
                    MealPresenter.printDetails(meal)
                    break
                } else if (input == "n") {
                        meal = useCase.execute()
                } else {
                    viewer.log("Invalid input. Please enter 'y' or 'n'.")
                }
            }
        } catch (_: EmptyMealsException) {
            viewer.log("No meals in the database.")
        } catch (e: NoMealFoundException) {
            viewer.log(
                """There is no high calories meal at the moment
                |please try again later.
            """.trimMargin()
            )

        } catch (e: Exception) {
            viewer.log("There something wrong happened when retrieving the data")
        }
    }


    companion object {
        const val FEATURE_ID = 13
        const val FEATURE_NAME = "Suggest high calorie meal"
    }
}
