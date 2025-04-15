package org.example.di

import org.example.logic.EggFreeSweetsUseCase
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.example.logic.IraqiMealsIdentifierUseCase
import org.example.logic.SearchMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SearchMealUseCase(get()) }
    single { EggFreeSweetsUseCase(get()) }
}