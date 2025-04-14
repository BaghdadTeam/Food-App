package org.example.logic

import model.Meal
import org.example.data.MealsProvider

class FilterQuickHealthyMealsUseCase(private val mealsProvider: MealsProvider) {
    private val allMeals =mealsProvider.meals
    fun getQuickHealthyMeals():List<String> {
        TODO("Implement the logic to filter meals ")
    }


}