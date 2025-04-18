package logic.use_case

import logic.MealsProvider
import model.Meal

class GuessMealGameUseCase (
    private val mealsProvider: MealsProvider
){
    fun guessMealPreparationTime(): Meal {

        val meal = mealsProvider.getMeals().random()
        return meal


    }
}