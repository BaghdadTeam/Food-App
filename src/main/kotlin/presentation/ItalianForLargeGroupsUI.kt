package org.example.presentation

import logic.usecase.MealsForLargeGroupUseCase
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class ItalianForLargeGroupsUI(
    private val useCase: MealsForLargeGroupUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val meals = useCase.execute()
            println("Matching Meals:")
            meals.forEachIndexed { index, meal ->
                println("${index + 1} - ${meal.name}")
            }
        } catch (_: EmptyMeals) {
            println("No meals in the database.")
        } catch (e: NoElementMatch) {
            println(
                """There is no meals for large group at the moment
                |please try again later
            """.trimMargin()
            )
        } catch (e: Exception) {
            println("There is something happened when retrieving data")
        }
    }

    companion object {
        const val FEATURE_ID = 15
        const val FEATURE_NAME = "Italian food suggestions for large groups"
    }
}
