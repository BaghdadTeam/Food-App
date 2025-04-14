package org.example

import org.example.data.MealsProvider
import org.example.di.appModule
import org.example.di.useCaseModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    println("Total meals: ${MealsProvider.meals.size}")
}