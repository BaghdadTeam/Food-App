package org.example.di

import logic.use_case.*
import org.example.logic.use_case.SuggestHighCalorieMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { FilterQuickHealthyMealsUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }
    single { SearchMealUseCase(get()) }

    single { EggFreeSweetsUseCase(get()) }
    single { SuggestEasyMealUseCase(get()) }
    single { SuggestHighCalorieMealUseCase(get()) }

    single { SuggestKetoMealUseCase(get()) }
    single { GuessMealGameUseCase(get()) }
    single { SeaFoodMealUseCase(get()) }

    single { IngredientGameUseCase(get()) }

    single { PotatoLovingMealsUseCase(get()) }

}
