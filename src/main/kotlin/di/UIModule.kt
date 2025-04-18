package org.example.di

import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.bind
import org.koin.dsl.module
import org.example.presentation.Feature
import org.example.presentation.GuessGameUI
import org.example.presentation.HighCalorieMealsUI
import org.example.presentation.IngredientGameUI
import org.example.presentation.IraqiMealsUI
import org.example.presentation.KetoFriendlyMealUI
import org.example.presentation.MealSearchUI
import org.example.presentation.PotatoLovingMealsUI
import org.example.presentation.QuickHealthyMealsUI
import org.example.presentation.SeafoodMealsUI
import org.example.presentation.SweetsWithNoEggsUI

val uiModule = module {

    factory { QuickHealthyMealsUI(get()) } bind Feature::class
    factory { IraqiMealsUI(get()) } bind Feature::class
    factory { GuessGameUI(get()) } bind Feature::class
    factory { SweetsWithNoEggsUI(useCase = get()) } bind Feature::class
    factory { KetoFriendlyMealUI(useCase = get()) } bind Feature::class
    factory { MealSearchUI(useCase = get()) } bind Feature::class
//    factory { SearchFoodByDateFeature(useCase = get()) }bind Feature::class
//    factory { GymHelperFeature(useCase = get()) }bind Feature::class
//    factory { ExploreOtherCountriesFeature(useCase = get()) }bind Feature::class
    factory { IngredientGameUI(useCase = get()) } bind Feature::class
//    factory { PotatoLovingMealsFeature(useCase = get()) }bind Feature::class
    factory { HighCalorieMealsUI(useCase = get()) } bind Feature::class
    factory { SeafoodMealsUI(useCase = get()) } bind Feature::class
//    factory { ItalianForLargeGroupsFeature(useCase = get()) }bind Feature::class

    factory { PotatoLovingMealsUI(useCase = get()) } bind Feature::class

    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.number }
    }

    single { FoodChangeMoodConsoleUI(get()) }
}