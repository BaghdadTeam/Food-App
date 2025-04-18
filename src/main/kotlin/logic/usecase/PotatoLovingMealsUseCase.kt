package logic.usecase

import logic.MealsProvider
import model.Meal

class PotatoLovingMealsUseCase(private val mealsProvider: MealsProvider) {

    fun execute(): List<Meal> {
        return mealsProvider.getMeals().filter { meal ->
            meal.ingredients!!.any { ingredient ->
                ingredient.contains("potato")
            }
        }
    }
}