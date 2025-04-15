package org.example.di

import SuggestHighCalorieMeal
import org.example.logic.EggFreeSweetsUseCase
import org.example.logic.FilterQuickHealthyMealsUseCase

import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }

}