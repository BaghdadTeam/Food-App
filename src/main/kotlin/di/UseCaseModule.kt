package org.example.di

import logic.MealSearchUseCase.SearchMealUseCase
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { SearchMealUseCase(get()) }

}