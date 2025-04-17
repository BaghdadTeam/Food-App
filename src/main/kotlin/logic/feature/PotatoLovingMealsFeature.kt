package logic.feature

import logic.use_case.PotatoLovingMealsUseCase
import model.Meal

class PotatoLovingMealsFeature(
    private val useCase: PotatoLovingMealsUseCase
) : Feature {
    override val number: Int = 12
    override val name: String = "I love potato list"

    override fun execute() {
        fun displayRandomPotatoMeals(potatoMeals: List<Meal>) {
            if (potatoMeals.isEmpty()) {
                println("No potato meals found in the Dataset!")
                return
            }

            println("=== 10 Random Potato Meals ===")
            println("Total potato meals found: ${potatoMeals.size}\n")

            val randomMeals = if (potatoMeals.size > 10) {
                potatoMeals.shuffled().take(10)
            } else {
                potatoMeals.shuffled()
            }

            randomMeals.forEach { meal ->
                println(
                    """
            ID: ${meal.id}
            Name: ${meal.name}
            Description: ${meal.description!!.take(60)}${if (meal.description.length > 60) "..." else ""}
            Calories: ${meal.nutrition?.calories ?: "N/A"}
            Ingredients: ${meal.ingredients!!.take(3).joinToString()}${if (meal.ingredients!!.size > 3) "..." else ""}
            ------------------------------------------
        """.trimIndent()
                )
            }
        }

    }
}
