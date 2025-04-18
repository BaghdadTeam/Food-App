package logic.usecase

import TextSearchUtil
import model.Meal
import logic.MealsProvider

class SearchMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun search(
        name: String
    ): List<Meal> =
        mealsProvider.getMeals().filter {
            findMealUsingKMP(it.name!!.lowercase(), name)
        }


    private fun findMealUsingKMP(
        mealName: String,
        pattern: String
    ): Boolean =
        TextSearchUtil.kmpSearch(mealName, pattern)
}
