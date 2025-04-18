package logic.use_case

import TextSearchUtil
import model.Meal
import logic.MealsProvider

class SearchMealUseCase(
    private val mealsProvider: MealsProvider
) {

    fun execute(name : String): List<Meal> {
        return mealsProvider.getMeals().filter {findMeal(it.name!!.lowercase(), name.lowercase()) }
    }

    private fun findMeal(mealName: String, name: String): Boolean {
        return TextSearchUtil.kmpSearch(mealName, name)
    }
}