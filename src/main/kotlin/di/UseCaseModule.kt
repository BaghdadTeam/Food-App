package org.example.di

import org.example.logic.FilterQuickHealthyMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }

}