package org.example.logic

import model.Meal

interface MealsProvider {

    fun getMeals(): List<Meal>
}