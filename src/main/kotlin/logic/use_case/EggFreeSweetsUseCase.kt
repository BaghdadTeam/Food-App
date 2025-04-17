package logic.use_case

import model.Meal
import org.example.logic.MealsProvider

class EggFreeSweetsUseCase(
    private val mealsProvider: MealsProvider
) {

    private val shownMeals = mutableSetOf<String>()

    fun suggestSweet(): Meal {
        return mealsProvider.getMeals()
            .filter(::isEggFreeSweet)
            .filterNot { shownMeals.contains(it.id.toString()) }
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: throw NoSuchElementException("There is no more egg free sweets")
    }


    fun dislikeSweet(meal: Meal) {
        shownMeals.add(meal.id.toString())
        suggestSweet()
    }


    private fun isEggFreeSweet(meal: Meal): Boolean {
        val sweetMeal = meal.tags?.any { it.contains("sweet", ignoreCase = true) } == true
        val containsEgg = meal.ingredients?.any { it.contains("egg", ignoreCase = true) } == true
        return sweetMeal and !containsEgg
    }
}