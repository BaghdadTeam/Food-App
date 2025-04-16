package org.example.logic

import model.Meal
import org.example.data.MealsProvider


class EggFreeSweetsUseCase(
    mealsProvider: MealsProvider
) {

    private val shownMeals = mutableSetOf<String>()


    private val eggFreeSweet = mealsProvider.meals.filter(::isEggFreeSweet)


    fun suggestSweet(): Meal {
        val notShownMeals = eggFreeSweet.filterNot { shownMeals.contains(it.id.toString()) }
        val newMeal = notShownMeals.randomOrNull() ?: throw NoSuchElementException("There is no more egg free sweets")
        return newMeal
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