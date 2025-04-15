package org.example.di

import logic.MealSearchUseCase.SearchMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealUseCase(get()) }
}
