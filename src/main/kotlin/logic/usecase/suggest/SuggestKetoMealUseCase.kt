package org.example.logic.usecase.suggest

import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class SuggestKetoMealUseCase(private val mealsProvider: MealsProvider) {

    private val alreadySuggestKetoMeals = mutableSetOf<String>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter(::isKetoMealAndNotSuggested)
            .takeIf { it.isNotEmpty() }
            ?.random()
          .also { alreadySuggestKetoMeals.add(it?.id.toString()) }
            ?: throw NoMealFoundException("There is no more unique keto Meals")
    }
    private fun isKetoMealAndNotSuggested(meal: Meal): Boolean {
        val nutrition = meal.nutrition
        return nutrition?.totalFat != null && nutrition.totalFat >= MIN_FAT &&
                nutrition.carbohydrates != null && nutrition.carbohydrates < MAX_CARBS &&
                nutrition.sugar != null && nutrition.sugar < MAX_SUGAR &&
                nutrition.protein != null && nutrition.protein in PROTEIN_RANGE &&
                meal.id.toString() !in alreadySuggestKetoMeals

    }
    private companion object {
        const val MIN_FAT = 15
        const val MAX_CARBS = 10
        const val MAX_SUGAR = 5
        val PROTEIN_RANGE = 10.0..30.0
    }
}