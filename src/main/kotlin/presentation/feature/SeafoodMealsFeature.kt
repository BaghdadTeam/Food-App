package presentation.feature

import logic.MealsProvider
import logic.use_case.SeaFoodMealUseCase

class SeafoodMealsFeature(
    private val useCase: SeaFoodMealUseCase,
) : Feature {
    override val number: Int = 14
    override val name: String = "Show seafood meals sorted by protein"

    override fun execute() {
        val rankedMeals = useCase.rankSeafoodMeals()
        println("Rank    Name    Protein")
        rankedMeals.forEach { (rank , meal)->
            println("${rank+1}      ${meal.name}     ${meal.nutrition!!.protein}")
        }
    }
}

