package org.example.presentation

import logic.usecase.EggFreeSweetsUseCase
import org.example.utils.MealPresenter

class SweetsWithNoEggsUI(private val useCase: EggFreeSweetsUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            while (true) {
                val sweet = useCase.execute()
                println("How about this sweet: ${sweet.name}")
                print("Do you like it? (yes/no): ")
                val choice = readln().trim()
                if (choice.contains("y", ignoreCase = true)) {
                    MealPresenter.printDetails(meal = sweet)
                    break
                }
            }
        } catch (e: NoSuchElementException) {
            println("There is no egg free sweets found")
        } catch (e: Exception) {
            println(
                """Something wrong happened when retrieving the data.
                |please try again later
            """.trimMargin()
            )
        }
    }

    companion object {
        const val FEATURE_ID = 6
        const val FEATURE_NAME = "Sweets with no eggs"
    }
}
