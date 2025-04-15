package org.example.di

import SuggestHighCalorieMeal
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.example.logic.IraqiMealsIdentifierUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SuggestHighCalorieMeal(get())}
}
