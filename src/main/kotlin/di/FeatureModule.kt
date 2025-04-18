package org.example.di

import logic.feature.*
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.bind
import org.koin.dsl.module

val FeatureModule = module {

    factory { QuickHealthyMealsFeature(get()) } bind Feature::class
    factory { IraqiMealsFeature(get()) } bind Feature::class
    factory { GuessGameFeature(get()) } bind Feature::class
    factory { SweetsWithNoEggsFeature(useCase = get()) } bind Feature::class
    factory { KetoFriendlyMealFeature(useCase = get()) } bind Feature::class
    factory { MealSearchFeature(useCase = get()) } bind Feature::class
//    factory { SearchFoodByDateFeature(useCase = get()) }bind Feature::class
//    factory { GymHelperFeature(useCase = get()) }bind Feature::class
//    factory { ExploreOtherCountriesFeature(useCase = get()) }bind Feature::class
    factory { IngredientGameFeature(useCase = get()) } bind Feature::class
//    factory { PotatoLovingMealsFeature(useCase = get()) }bind Feature::class
    factory { HighCalorieMealsFeature(useCase = get()) } bind Feature::class
    factory { SeafoodMealsFeature(useCase = get()) } bind Feature::class
//    factory { ItalianForLargeGroupsFeature(useCase = get()) }bind Feature::class

    factory { PotatoLovingMealsFeature(useCase = get()) } bind Feature::class
    factory { SearchFoodByDateFeature(useCase = get()) } bind Feature::class

    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.number }
    }

    single { FoodChangeMoodConsoleUI(get()) }

}