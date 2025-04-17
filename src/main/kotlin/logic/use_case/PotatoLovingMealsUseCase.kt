package logic.use_case;

import logic.MealsProvider;
import model.Meal

class PotatoLovingMealsUseCase (private val mealsProvider: MealsProvider){

        fun findPotatoMeals(): List<Meal> {
            return mealsProvider.getMeals().filter { meal ->
                meal.ingredients!!.any { ingredient ->
                    ingredient.contains("potato")
                }
            }
        }

    }

