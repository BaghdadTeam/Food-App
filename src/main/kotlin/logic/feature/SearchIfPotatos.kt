package logic.feature

import logic.use_case.PotatoFilterUseCase
import model.Meal

class SearchIfPotatos(private val useCase: PotatoFilterUseCase):Feature {

    private fun displayRandomPotatoMeals(potatoMeals: List<Meal>) {
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

    override val number: Int
        get() = 12
    override val name: String
        get() = "Search for Potatos"

    override fun execute() {
        val potatosMeal = useCase.findPotatoMeals()
        displayRandomPotatoMeals(potatosMeal)
    }

}