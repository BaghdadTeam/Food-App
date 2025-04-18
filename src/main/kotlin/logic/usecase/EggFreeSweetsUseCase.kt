package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class EggFreeSweetsUseCase(
    private val mealsProvider: MealsProvider
) {

    private val seenMeals = mutableSetOf<String>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMeals("No meals found")
        return mealsProvider.getMeals()
            .filter(::isEggFreeSweet)
            .takeIf { it.isNotEmpty() }
            ?.random()
            .also { seenMeals.add(it?.id.toString()) }
            ?: throw NoElementMatch("There is no more egg free sweets")
    }


    private fun isEggFreeSweet(meal: Meal): Boolean {
        val sweetMeal = meal.tags?.any { it.contains("sweet", ignoreCase = true) } == true
        if (!sweetMeal) return false

        val containsEgg = meal.ingredients?.any { it.contains("egg", ignoreCase = true) } == true
        if (containsEgg) return false

        val seen = seenMeals.contains(meal.id.toString())
        return !seen
    }
}