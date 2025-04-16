package org.example

import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.*
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.example.logic.SuggestKetoMealUseCase
import org.example.presentation.FoodChangeMoodConsoleUI
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
        println("name${index + 1} - ${meal.name} --->(time: ${meal.preparationTime} mins, ingredients: ${meal.ingredients?.size}, steps: ${meal.steps.size})")
    }

    val suggestKetoMealUseCase: SuggestKetoMealUseCase = getKoin().get()
    val ingredientGameUseCase: IngredientGameUseCase = getKoin().get()

    val ui: FoodChangeMoodConsoleUI = getKoin().get()
    ui.start()

}
