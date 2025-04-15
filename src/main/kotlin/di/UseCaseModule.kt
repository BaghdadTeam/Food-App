package org.example.di

import SuggestHighCalorieMeal
import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { SuggestHighCalorieMeal(get()) }
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SearchMealUseCase(get()) }
    single { EggFreeSweetsUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
}