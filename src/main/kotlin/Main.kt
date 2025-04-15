package org.example

import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val quickHealthyMealsUseCase: FilterQuickHealthyMealsUseCase = getKoin().get()
    val quickHealthyMeals = quickHealthyMealsUseCase.getQuickHealthyMeals(5)


}