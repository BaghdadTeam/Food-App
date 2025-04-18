package org.example.di


import logic.use_case.SearchFoodByDateUseCase
import GymMealHelperUseCase
import logic.usecase.*
import org.example.logic.*
import org.koin.dsl.module
import kotlin.math.sin

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