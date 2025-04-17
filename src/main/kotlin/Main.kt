package org.example

import org.example.di.FeatureModule
import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule, FeatureModule)
    }
    val ui: FoodChangeMoodConsoleUI = getKoin().get()
    ui.start()
}
