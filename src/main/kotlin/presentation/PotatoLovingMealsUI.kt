package org.example.presentation

import logic.usecase.PotatoLovingMealsUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class PotatoLovingMealsUI(
    private val useCase: PotatoLovingMealsUseCase, private val viewer: Viewer
) : Feature {

    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            val potatoMeals = useCase.execute()

            // Throw EmptyMealsException if no meals are found
            if (potatoMeals.isEmpty()) {
                throw EmptyMealsException("No potato meals available in the dataset.")
            }

            // Process meals if there are any
            viewer.log("=== 10 Random Potato Meals ===")
            viewer.log("Total potato meals found: ${potatoMeals.size}\n")

            val randomMeals = potatoMeals.shuffled().take(NUMBER_OF_POTATO_MEALS.coerceAtMost(potatoMeals.size))

            randomMeals.forEach { meal ->
                viewer.log(
                    """
                    ID: ${meal.id}
                    Name: ${meal.name}
                    Description: ${meal.description?.take(MAX_DESCRIPTION_LETTERS) ?: "No Description"}${if ((meal.description?.length ?: 0) > MAX_DESCRIPTION_LETTERS) "..." else ""}
                    Calories: ${meal.nutrition?.calories ?: "N/A"}
                    Ingredients: ${
                        meal.ingredients?.take(MAX_INGREDIENTS_NUMBER)?.joinToString() ?: "No Ingredients"
                    }${if ((meal.ingredients?.size ?: 0) > MAX_INGREDIENTS_NUMBER) "..." else ""}
                    ------------------------------------------
                    """.trimIndent()
                )
            }

        } catch (e: NoMealFoundException) {
            viewer.log("No meals available at all: ${e.localizedMessage}")
        } catch (e: EmptyMealsException) {
            viewer.log("An unexpected error occurred while fetching potato meals: ${e.localizedMessage}")
        } catch (e: Exception) {
            viewer.log("An unexpected error occurred: ${e.localizedMessage}")
        }
    }

    companion object {
        const val FEATURE_ID = 12
        const val FEATURE_NAME = "I love potato list"
        const val MAX_DESCRIPTION_LETTERS = 60
        const val MAX_INGREDIENTS_NUMBER = 3
        const val NUMBER_OF_POTATO_MEALS = 10
    }
}
