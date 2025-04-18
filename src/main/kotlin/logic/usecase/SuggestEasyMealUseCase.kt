package logic.usecase

import model.Meal
import logic.MealsProvider

class SuggestEasyMealUseCase(private val mealsProvider: MealsProvider) {

    fun getRandomEasyMeals(): List<Meal> {
        return mealsProvider.getMeals()
            .filter(::isEasyMeal)
            .shuffled()
            .takeIf { it.isNotEmpty() }
            ?.take(10)
            ?: throw NoSuchElementException("There is no more easy to make meals")
    }

    private fun isEasyMeal(meal: Meal): Boolean =
        (meal.preparationTime != null && meal.preparationTime <= 30) &&
                (meal.ingredients?.size != null && meal.ingredients.size <= 5) &&
                (meal.steps.size <= 6)

}