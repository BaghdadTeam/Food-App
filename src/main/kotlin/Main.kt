package org.example

import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.example.logic.SuggestEasyMealUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val quickHealthyMealsUseCase: FilterQuickHealthyMealsUseCase = getKoin().get()
    val quickHealthyMeals = quickHealthyMealsUseCase.getQuickHealthyMeals(5)


    val suggestEasyMealsUseCase: SuggestEasyMealUseCase = getKoin().get()
    val easyMeals = suggestEasyMealsUseCase.getRandomEasyMeals()
    println("Easy Meals:")
    easyMeals.forEachIndexed { index, meal ->
        println("name${index + 1} - ${meal.name} --->(time: ${meal.timeToCock} mins, ingredients: ${meal.ingredients?.size}, steps: ${meal.steps.size})")
    }


}