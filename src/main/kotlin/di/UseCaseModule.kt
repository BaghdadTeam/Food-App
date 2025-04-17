package org.example.di

import logic.use_case.EggFreeSweetsUseCase
import logic.use_case.FilterQuickHealthyMealsUseCase
import logic.use_case.GuessMealGameUseCase
import logic.use_case.IngredientGameUseCase
import logic.use_case.IraqiMealsIdentifierUseCase
import logic.use_case.SeaFoodMealUseCase
import logic.use_case.SearchMealUseCase
import logic.use_case.SuggestEasyMealUseCase
import logic.use_case.SuggestKetoMealUseCase
import org.example.logic.use_case.SuggestHighCalorieMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SearchMealUseCase(get()) }

    single { EggFreeSweetsUseCase(get()) }
    single { SuggestEasyMealUseCase(get()) }
    single { SuggestHighCalorieMealUseCase(get()) }

    single { SuggestKetoMealUseCase(get()) }
    single { GuessMealGameUseCase(get()) }
    single { SeaFoodMealUseCase(get()) }

    single { IngredientGameUseCase(get()) }

}
