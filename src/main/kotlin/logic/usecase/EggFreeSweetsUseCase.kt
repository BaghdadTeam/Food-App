package logic.usecase

import model.Meal
import logic.MealsProvider

class EggFreeSweetsUseCase(
    private val mealsProvider: MealsProvider
) {

    private val seenMeals = mutableSetOf<String>()

    fun suggestSweet(): Meal {
        return mealsProvider.getMeals()
            .filter(::isEggFreeSweet)
            .takeIf { it.isNotEmpty() }
            ?.random()
            .also { seenMeals.add(it?.id.toString()) }
            ?: throw NoSuchElementException("There is no more egg free sweets")
    }


    fun dislikeSweet(meal: Meal) {
        seenMeals.add(meal.id.toString())
        suggestSweet()
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