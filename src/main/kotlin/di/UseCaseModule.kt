package org.example.di

import logic.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single { EggFreeSweetsUseCase(get()) }
    single { ExploreOtherCountriesFoodCultureUseCase(get()) }
    single { FilterQuickHealthyMealsUseCase(get()) }

    single { GuessMealGameUseCase(get()) }
    single { IngredientGameUseCase(get()) }
    single { IraqiMealsIdentifierUseCase(get()) }

    single { MealsForLargeGroupUseCase(get()) }
    single { PotatoLovingMealsUseCase(get()) }
    single { SeaFoodMealUseCase(get()) }

    single { SearchMealUseCase(get()) }
    single { SuggestEasyMealUseCase(get()) }
    single { SuggestHighCalorieMealUseCase(get()) }

    single { SuggestKetoMealUseCase(get()) }
    single { GymMealHelperUseCase(get()) }
    single { SearchFoodByDateUseCase(get()) }
}