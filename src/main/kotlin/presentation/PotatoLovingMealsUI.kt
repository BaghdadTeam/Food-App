package org.example.presentation

import logic.usecase.PotatoLovingMealsUseCase

class PotatoLovingMealsUI(
    private val useCase: PotatoLovingMealsUseCase, private val viewer: Viewer
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        val potatoMeals = useCase.execute()
        if (potatoMeals.isEmpty()) {
            viewer.log("No potato meals found in the Dataset!")
            return
        }

        viewer.log("=== 10 Random Potato Meals ===")
        viewer.log("Total potato meals found: ${potatoMeals.size}\n")

        val randomMeals = if (potatoMeals.size > NUMBER_OF_POTATO_MEALS) {
            potatoMeals.shuffled().take(NUMBER_OF_POTATO_MEALS)
        } else {
            potatoMeals.shuffled()
        }

        randomMeals.forEach { meal ->
            viewer.log(
                """
            ID: ${meal.id}
            Name: ${meal.name}
            Description: ${meal.description!!.take(MAX_DESCRIPTION_LETTERS)}${if (meal.description.length > MAX_DESCRIPTION_LETTERS) "..." else ""}
            Calories: ${meal.nutrition?.calories ?: "N/A"}
            Ingredients: ${
                    meal.ingredients!!.take(MAX_INGREDIENTS_NUMBER).joinToString()
                }${if (meal.ingredients.size > MAX_INGREDIENTS_NUMBER) "..." else ""}
            ------------------------------------------
        """.trimIndent()
            )
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
