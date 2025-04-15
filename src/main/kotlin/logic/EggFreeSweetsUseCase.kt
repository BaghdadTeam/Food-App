package org.example.logic

import model.Meal
import org.example.data.MealsProvider

/**
 * Use case for suggesting egg-free sweet meals.
 *
 * @param mealsProvider Provides the list of meals to filter and suggest from.
 */
class EggFreeSweetsUseCase(
    mealsProvider: MealsProvider
) {

    private val shownMeals = mutableSetOf<String>()

    /**
     * Filters the meals provided by the `MealsProvider` to include only egg-free sweets.
     */
    private val eggFreeSweet = mealsProvider.meals.filter(::isSweetContainEgg)

    /**
     * Suggests a random egg-free sweet meal that has not been shown yet.
     *
     * @return A `Meal` object representing the suggested sweet.
     * @throws NoSuchElementException If there are no more egg-free sweets to suggest.
     */
    fun suggestSweet(): Meal {
        val notShownMeals = eggFreeSweet.filterNot { shownMeals.contains(it.id.toString()) }
        val newMeal = notShownMeals.randomOrNull() ?: throw NoSuchElementException("There is no more egg free sweets")
        return newMeal
    }

    /**
     * Marks a meal as disliked and suggests another egg-free sweet meal.
     *
     * @param meal The `Meal` object to be marked as disliked.
     */
    fun dislikeSweet(meal: Meal) {
        shownMeals.add(meal.id.toString())
        suggestSweet()
    }

    /**
     * Checks if a meal is a sweet and does not contain eggs.
     *
     * @param meal The `Meal` object to check.
     * @return `true` if the meal is a sweet and does not contain eggs, `false` otherwise.
     */
    private fun isSweetContainEgg(meal: Meal): Boolean {
        val sweetMeal = meal.tags?.any { it.contains("sweet", ignoreCase = true) } == true
        val containsEgg = meal.ingredients?.any { it.contains("egg", ignoreCase = true) } == true
        return sweetMeal and !containsEgg
    }
}