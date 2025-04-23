package org.example.di

import logic.usecase.GuessMealGameUseCase

import org.example.presentation.*
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.feature.GymHelperUI

val uiModule = module {

    factory { QuickHealthyMealsUI(get()) } bind Feature::class
    factory { IraqiMealsUI(get()) } bind Feature::class
    factory { GuessGameUI(get<GuessMealGameUseCase>()) } bind Feature::class
    //factory { GuessGameUI(useCase = get()) } bind Feature::class

    factory { SweetsWithNoEggsUI(useCase = get()) } bind Feature::class
    factory { KetoFriendlyMealUI(useCase = get()) } bind Feature::class
    factory { MealSearchUI(useCase = get()) } bind Feature::class

    factory { SearchFoodByDateUI(useCase = get()) } bind Feature::class
    factory { ExploreOtherCountriesUI(useCase = get()) } bind Feature::class
    factory { IngredientGameUI(useCase = get()) } bind Feature::class
    
    factory { HighCalorieMealsUI(useCase = get()) } bind Feature::class
    factory { SeafoodMealsUI(useCase = get()) } bind Feature::class
    factory { GymHelperUI(useCase = get()) } bind Feature::class
    
    factory { SuggestEasyMealUI(useCase = get()) } bind Feature::class
    factory { ExploreOtherCountriesUI(useCase = get()) } bind Feature::class
    factory { IngredientGameUI(useCase = get()) } bind Feature::class
    
    factory { ItalianForLargeGroupsUI(useCase = get()) } bind Feature::class
    factory { PotatoLovingMealsUI(useCase = get()) } bind Feature::class
    factory { SearchFoodByDateUI(get()) } bind Feature::class

    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.id }
    }

    single { FoodChangeMoodConsoleUI(get()) }
}
