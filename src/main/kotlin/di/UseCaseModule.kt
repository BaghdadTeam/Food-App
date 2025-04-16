package org.example.di

import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SearchMealUseCase(get()) }
    single { EggFreeSweetsUseCase(get()) }
    single { SuggestHighCalorieMealUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { GuessMealGameUseCase(get()) }
    single { IngredientGameUseCase(get()) }
}
