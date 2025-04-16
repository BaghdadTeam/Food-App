package org.example.logic

import model.Meal
import org.example.data.DefaultMealsProvider

class SuggestKetoMealUseCase(private val mealsProvider: MealsProvider) {
    private val alreadySuggestKetoMeals = mutableSetOf<Meal>()


    fun getKetoMealSuggest(): Meal {
        val availableMeals =
            mealsProvider.getMeals()
                .filter(::isKetoMeal)
                .filterNot { ketoMeal -> ketoMeal in alreadySuggestKetoMeals }

        if (availableMeals.isEmpty()) throw NoSuchElementException("There is no more unique keto Meals")
        return availableMeals.random().also { ketoMeal -> alreadySuggestKetoMeals.add(ketoMeal) }
    }

    private fun isKetoMeal(meal: Meal): Boolean {
        val nutrition = meal.nutrition
        return nutrition != null && nutrition.totalFat!! >= 15 &&
                nutrition.carbohydrates!! < 10 &&
                nutrition.sugar!! < 5 && nutrition.protein!! in 10.0..30.0
    }
}