package logic.usecase

import TextSearchUtil
import model.Meal
import logic.MealsProvider

class SearchMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(name: String): List<Meal> =
        mealsProvider.getMeals().filter {
            findMeal(it.name!!.lowercase(), name.lowercase())
        }


    private fun findMeal(
        mealName: String,
        pattern: String
    ): Boolean =
        TextSearchUtil.kmpSearch(mealName, pattern)
}