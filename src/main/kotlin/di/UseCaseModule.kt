package org.example.di

import org.example.logic.FilterQuickHealthyMealsUseCase
import org.example.logic.SuggestKetoMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single {SuggestKetoMealUseCase(get())}
}