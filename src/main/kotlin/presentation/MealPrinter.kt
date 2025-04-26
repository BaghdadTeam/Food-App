package org.example.presentation

import model.Meal

interface MealPrinter {
    fun print(meals: List<Meal>, isGymHelperUI: Boolean = false)
}
