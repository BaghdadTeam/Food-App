package org.example

import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.*
import logic.use_case.FilterQuickHealthyMealsUseCase
import logic.use_case.SuggestKetoMealUseCase
import logic.feature.Feature
import logic.feature.QuickHealthyMealsFeature
import logic.use_case.IngredientGameUseCase
import logic.use_case.SuggestEasyMealUseCase
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

     val features: Map<Int, Feature> = mapOf(
        1 to QuickHealthyMealsFeature(quickHealthyMealsUseCase),
//        2 to MealSearchFeature(mealSearchUseCase),
        3 to IraqiMealsFeature(iraqiMealsUseCase),
        5 to GuessGameFeature(guessGameUseCase),
        6 to SweetsWithNoEggsFeature(sweetsWithNoEggsUseCase),
        7 to KetoFriendlyMealFeature(ketoFriendlyMealUseCase),
        // Add others as needed
    )

}
