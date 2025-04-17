package logic.use_case

import model.Meal
import logic.MealsProvider

class SuggestKetoMealUseCase(private val mealsProvider: MealsProvider) {

    private val alreadySuggestKetoMeals = mutableSetOf<Meal>()

    fun getKetoMealSuggest(): Meal {
        return mealsProvider.getMeals()
            .filter(::isKetoMealAndNotSuggested)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { alreadySuggestKetoMeals.add(it) }
            ?: throw NoSuchElementException("There is no more unique keto Meals")
    }

    private fun isKetoMealAndNotSuggested(meal: Meal): Boolean {
        val nutrition = meal.nutrition
        return nutrition != null && nutrition.totalFat!! >= 15 &&
                nutrition.carbohydrates!! < 10 &&
                nutrition.sugar!! < 5 && nutrition.protein!! in 10.0..30.0&&meal !in alreadySuggestKetoMeals

    }
}